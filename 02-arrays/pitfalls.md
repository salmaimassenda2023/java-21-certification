# Pitfalls: Arrays, Sorting, Searching, Comparable and Comparator

> Exam traps and common bugs for chapter 02. Every example must be **run in the IDE** to confirm the result.
> Format: **Trap → What happens → Why → Fix / Rule**.

## Quick summary

| # | Trap | Category |
|---|---|---|
| 1 | Declaration syntax: `int[] a, b[];` | Syntax |
| 2 | Array initializer only at declaration | Syntax |
| 3 | `new int[][3]` does not compile | Syntax |
| 4 | Default values | Basics |
| 5 | `NegativeArraySizeException` | Runtime |
| 6 | Printing an array | Basics |
| 7 | `==` and `equals` compare references | Comparing |
| 8 | `Arrays.equals` is not deep | Comparing |
| 9 | `Arrays.compare` and `Arrays.mismatch` details | Comparing |
| 10 | `clone()` is shallow | Copying |
| 11 | `copyOf` and `copyOfRange` rules | Copying |
| 12 | `System.arraycopy` exceptions | Copying |
| 13 | `binarySearch` on unsorted array | Searching |
| 14 | `binarySearch` negative result | Searching |
| 15 | `binarySearch` with duplicates | Searching |
| 16 | `binarySearch` with a different Comparator | Searching |
| 17 | `sort(from, to)`: `to` is exclusive | Sorting |
| 18 | String sort order | Sorting |
| 19 | `sort(int[], Comparator)` does not compile | Sorting |
| 20 | `ClassCastException` at runtime | Sorting |
| 21 | `null` in a sorted array | Sorting |
| 22 | `compareTo` subtraction overflow | Comparable |
| 23 | `compareTo` not consistent with `equals` | Comparable |
| 24 | `reversed()` reverses the whole chain | Comparator |
| 25 | `Arrays.asList` is fixed-size | Arrays and lists |
| 26 | `Arrays.asList(int[])` has size 1 | Arrays and lists |
| 27 | Covariance and `ArrayStoreException` | Types |
| 28 | Enhanced `for` and the loop variable | Loops |
| 29 | `length` vs `length()` vs `size()` | Syntax |
| 30 | Index type and `ArrayIndexOutOfBoundsException` | Runtime |

---

## Syntax and basics

### 1. Declaration syntax: the brackets belong to each variable

```java
int[] a, b;      // a and b are both int[]
int a[], b;      // a is int[], b is just int
int[] a, b[];    // a is int[], b is int[][]
```

**Why:** `int[]` applies to every variable in the line, but brackets placed after a variable name apply to that variable only.
**Rule:** declare one variable per line and avoid brackets after the name.

### 2. Array initializer `{...}` works only at declaration

```java
int[] a = {1, 2, 3};      // OK
a = {4, 5, 6};            // does NOT compile
a = new int[]{4, 5, 6};   // OK
```

### 3. Only the first dimension can be left empty

```java
int[][] m1 = new int[3][];    // OK (jagged array, rows are null)
int[][] m2 = new int[][3];    // does NOT compile
int[][] m3 = new int[2][3];   // OK
```

You cannot specify the size in `new int[3]` and the initializer in the same expression:
```java
int[] x = new int[3]{1, 2, 3};   // does NOT compile
```

### 4. Default values

```java
int[] a = new int[3];          // [0, 0, 0]
boolean[] b = new boolean[2];  // [false, false]
String[] s = new String[2];    // [null, null]
int[][] m = new int[2][];      // [null, null]  (rows do not exist yet)
```

**Trap:** `m[0][0] = 1;` throws `NullPointerException` because `m[0]` is `null`.

### 5. Negative size

```java
int[] a = new int[-1];   // compiles, throws NegativeArraySizeException at runtime
```

### 6. Printing an array

```java
int[] a = {1, 2, 3};
System.out.println(a);                      // [I@1b6d3586  (type + hash, not the content)
System.out.println(Arrays.toString(a));     // [1, 2, 3]
System.out.println(Arrays.deepToString(new int[][]{{1}, {2, 3}}));   // [[1], [2, 3]]

char[] c = {'h', 'i'};
System.out.println(c);                      // hi   (println has a special overload for char[])
```

**Rule:** `Arrays.toString` for one dimension, `Arrays.deepToString` for nested arrays. `char[]` is the special case.

---

## Comparing

### 7. `==` and `equals` compare references

```java
int[] x = {1, 2, 3};
int[] y = {1, 2, 3};
x == y;                  // false
x.equals(y);             // false  (arrays do not override equals)
Arrays.equals(x, y);     // true
x.hashCode() == y.hashCode();   // almost certainly false
Arrays.hashCode(x) == Arrays.hashCode(y);   // true
```

**Consequence:** an array used as a `HashMap` key or in a `HashSet` uses identity, not content.

### 8. `Arrays.equals` is not deep

```java
int[][] m1 = {{1, 2}, {3}};
int[][] m2 = {{1, 2}, {3}};
Arrays.equals(m1, m2);       // false  (compares the inner arrays with equals → references)
Arrays.deepEquals(m1, m2);   // true
```

**Rule:** nested arrays need `deepEquals`.

### 9. `compare` and `mismatch` details

```java
Arrays.compare(new int[]{1,2,3}, new int[]{1,2,4});   // negative
Arrays.compare(new int[]{1,2,3}, new int[]{1,2});     // positive (longer array is "greater")
Arrays.compare(new int[]{1,2,3}, new int[]{1,2,3});   // 0

Arrays.mismatch(new int[]{1,2,3}, new int[]{1,2,4});  // 2
Arrays.mismatch(new int[]{1,2,3}, new int[]{1,2});    // 2  (length of the shorter one)
Arrays.mismatch(new int[]{1,2,3}, new int[]{1,2,3});  // -1 (no mismatch)
```

**Rules:**
- Do not depend on the exact negative or positive value of `compare`; only the sign matters.
- `mismatch` returns `-1` when the arrays are equal, not `0`.
- A `null` array is considered smaller than a non-null array in `compare`, and two `null` arrays are equal. `mismatch` throws `NullPointerException` for `null`.

---

## Copying

### 10. `clone()` is shallow

```java
int[][] original = {{1, 2}, {3, 4}};
int[][] copy = original.clone();
copy[0][0] = 99;
System.out.println(original[0][0]);   // 99  (inner arrays are shared)
copy[1] = new int[]{7, 8};            // replaces the reference in the copy only
System.out.println(original[1][0]);   // 3
```

**Same for** `Arrays.copyOf`, `copyOfRange` and `System.arraycopy`. For an array of objects, the objects are shared.

### 11. `copyOf` and `copyOfRange`

```java
int[] a = {1, 2, 3};
Arrays.copyOf(a, 5);           // [1, 2, 3, 0, 0]   padding with default value
Arrays.copyOf(a, 2);           // [1, 2]            truncated
Arrays.copyOfRange(a, 1, 5);   // [2, 3, 0, 0]      'to' may be beyond the length → padding
Arrays.copyOfRange(a, 3, 5);   // [0, 0]            'from' == length is allowed
Arrays.copyOfRange(a, 4, 5);   // ArrayIndexOutOfBoundsException (from > length)
Arrays.copyOfRange(a, 2, 1);   // IllegalArgumentException (from > to)
Arrays.copyOf(a, -1);          // NegativeArraySizeException
```

**Rules:** `from` must be between `0` and `length`. `to` can be larger than the length. `from > to` is an `IllegalArgumentException`.

### 12. `System.arraycopy`

```java
System.arraycopy(src, srcPos, dest, destPos, length);
```

- Throws `ArrayIndexOutOfBoundsException` if any range goes outside the arrays. It does **not** pad or resize.
- Throws `NullPointerException` if `src` or `dest` is `null`.
- Throws `ArrayStoreException` if the types are incompatible (for example `int[]` to `long[]`, or an element cannot be stored in the destination type).
- Overlapping ranges in the same array are **handled correctly**, as if a temporary copy was made.

```java
int[] a = {1, 2, 3, 4, 5};
System.arraycopy(a, 0, a, 1, 4);
// a = [1, 1, 2, 3, 4]   (shift right by 1, works correctly)
```

---

## Searching

### 13. `binarySearch` on an unsorted array

```java
int[] a = {30, 10, 20};
Arrays.binarySearch(a, 10);   // undefined result: no exception, may be wrong
```

**Rule:** the array must be sorted **before** the search. Otherwise the result is undefined.

### 14. Negative result when not found

```java
int[] a = {10, 20, 30, 40};
Arrays.binarySearch(a, 25);   // -3   = -(2) - 1   (insertion point is 2)
Arrays.binarySearch(a, 5);    // -1   = -(0) - 1
Arrays.binarySearch(a, 50);   // -5   = -(4) - 1
```

**Formula:** `index = -(result) - 1` gives you the insertion point.
**Trap:** checking `if (result == -1)` for "not found" is wrong. Use `result < 0`.

### 15. Duplicates

```java
int[] a = {1, 2, 2, 2, 3};
Arrays.binarySearch(a, 2);   // could return 1, 2 or 3
```

**Rule:** with duplicates, the API does not guarantee which index is returned.

### 16. The search must use the same ordering as the sort

```java
Student[] arr = ...;
Arrays.sort(arr, byName);
Arrays.binarySearch(arr, key, byName);   // OK
Arrays.binarySearch(arr, key, byGpa);    // undefined result
```

---

## Sorting

### 17. `Arrays.sort(a, from, to)`: `to` is exclusive

```java
int[] a = {5, 4, 3, 2, 1};
Arrays.sort(a, 1, 4);
// a = [5, 2, 3, 4, 1]   (indexes 1, 2, 3 sorted; index 4 untouched)
```

`from > to` throws `IllegalArgumentException`; a range outside the array throws `ArrayIndexOutOfBoundsException`.

### 18. String order follows Unicode values, not the dictionary

```java
String[] s = {"banana", "Apple", "cherry", "apple", "10", "2"};
Arrays.sort(s);
// [10, 2, Apple, apple, banana, cherry]
```

**Why:**
- digits (`'0'` to `'9'`) come before uppercase letters, and uppercase letters come before lowercase letters
- `"10"` comes before `"2"` because the comparison is character by character (`'1' < '2'`)

**Fix:** `Arrays.sort(s, String.CASE_INSENSITIVE_ORDER)` for case-insensitive order. For human languages, use `java.text.Collator`.

### 19. `Arrays.sort(int[], Comparator)` does not compile

```java
Arrays.sort(new int[]{3, 1, 2}, Collections.reverseOrder());       // compile error
Arrays.sort(new Integer[]{3, 1, 2}, Collections.reverseOrder());   // OK
```

**Why:** generics do not accept primitives.

### 20. `ClassCastException` at runtime

```java
class Person { String name; }

Person[] people = { new Person(), new Person() };
Arrays.sort(people);   // compiles! throws ClassCastException at runtime
```

**Why:** `Arrays.sort(Object[])` accepts any object array. The cast to `Comparable` happens at runtime.
**Fix:** implement `Comparable<Person>`, or pass a `Comparator`.

### 21. `null` elements

```java
String[] s = {"b", null, "a"};
Arrays.sort(s);   // NullPointerException
Arrays.sort(s, Comparator.nullsFirst(Comparator.naturalOrder()));   // [null, a, b]
```

---

## Comparable and Comparator

### 22. Subtraction in `compareTo` can overflow

```java
public int compareTo(Student o) { return this.id - o.id; }   // BUG
```

```java
int a = Integer.MAX_VALUE;
int b = -1;
a - b;   // overflow → negative value → says "a is smaller" (wrong)
```

**Fix:** `Integer.compare(this.id, o.id)`. For `double`, use `Double.compare`.

### 23. `compareTo` not consistent with `equals`

```java
new BigDecimal("1.0").equals(new BigDecimal("1.00"));       // false (different scale)
new BigDecimal("1.0").compareTo(new BigDecimal("1.00"));    // 0

new HashSet<>(List.of(new BigDecimal("1.0"), new BigDecimal("1.00"))).size();  // 2
new TreeSet<>(List.of(new BigDecimal("1.0"), new BigDecimal("1.00"))).size();  // 1
```

**Rule:** `HashSet` uses `equals` and `hashCode`; `TreeSet` uses `compareTo` or the `Comparator`. If they disagree, the same data gives different results. Make your classes consistent: `compareTo(x) == 0` should mean `equals(x)`.

### 24. `reversed()` reverses the whole chain before it

```java
Comparator<Student> c1 = Comparator.comparing(Student::name)
                                   .thenComparing(Student::gpa)
                                   .reversed();
// reverses BOTH name and gpa

Comparator<Student> c2 = Comparator.comparing(Student::name)
                                   .thenComparing(Comparator.comparingDouble(Student::gpa).reversed());
// name ascending, gpa descending
```

**Rule:** to reverse one key only, put `reversed()` on that comparator, not at the end of the chain.

---

## Arrays and lists, types

### 25. `Arrays.asList` is a fixed-size list backed by the array

```java
String[] arr = {"a", "b", "c"};
List<String> list = Arrays.asList(arr);

list.set(0, "X");   // OK → arr[0] is now "X"
arr[1] = "Y";       // list.get(1) is now "Y"
list.add("d");      // UnsupportedOperationException
list.remove(0);     // UnsupportedOperationException
```

**Fix:** `new ArrayList<>(Arrays.asList(arr))` for a normal mutable list.
**Compare:** `List.of(...)` is fully immutable (`set` also throws) and rejects `null`.

### 26. `Arrays.asList(int[])` gives a list with one element

```java
int[] nums = {1, 2, 3};
Arrays.asList(nums).size();                    // 1  (List<int[]>)
Arrays.asList(new Integer[]{1, 2, 3}).size();  // 3
Arrays.asList(1, 2, 3).size();                 // 3  (autoboxing with varargs)
```

### 27. Covariance and `ArrayStoreException`

```java
Object[] objs = new String[2];
objs[0] = "ok";
objs[1] = 42;   // compiles, throws ArrayStoreException at runtime
```

Compare with generics, where the same mistake is a **compile-time** error:
```java
List<Object> list = new ArrayList<String>();   // does not compile
```

---

## Loops and runtime

### 28. The enhanced `for` loop variable is a copy

```java
int[] a = {1, 2, 3};
for (int x : a) {
    x = x * 2;   // changes only the local variable
}
// a is still [1, 2, 3]

String[] s = {"a", "b"};
for (String str : s) {
    str = "z";   // changes only the local reference
}
// s is still [a, b]
```

**But:** for an array of mutable objects, calling a method on the element **does** change the object (`p.setName("z")`), because the copy points to the same object.

### 29. `length` vs `length()` vs `size()`

| Type | How to get the size |
|---|---|
| Array | `arr.length` (field, no parentheses) |
| `String` | `str.length()` (method) |
| `List`, `Set`, `Map` | `list.size()` |

`arr.length()` and `str.length` do not compile.

### 30. Index type and out-of-bounds

```java
int[] a = new int[3];
a[3];           // ArrayIndexOutOfBoundsException (valid indexes: 0 to 2)
a[-1];          // ArrayIndexOutOfBoundsException
a['A' - 'A'];   // OK (char is promoted to int)
a[1L];          // does NOT compile (long is not allowed as an index)
```

**Rule:** the index must be `int` or a type that can be promoted to `int` (`byte`, `short`, `char`).

---

## My own mistakes (fill this while you study)

> This is your most valuable section: real mistakes from your own predictions.

| Date | Code | My prediction | Real result | Lesson |
|---|---|---|---|---|
| | | | | |
| | | | | |
| | | | | |

## Review checklist

- [ ] I ran every example in the IDE
- [ ] I can predict the result of traps 7, 8, 14, 18 and 25 without running them
- [ ] I can explain traps 10, 22, 23 and 27 in English
- [ ] I turned at least 10 of these traps into questions in `questions.md`