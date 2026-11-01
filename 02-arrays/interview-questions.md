# Interview Questions: Arrays, Comparable and Comparator

> Written in my own words. Practice: read the question, answer out loud in English for 1 to 2 minutes, then compare with my written answer.

## How to use this file

1. Cover the answer and try to answer first.
2. Say it out loud in English.
3. Check the answer, then fix it in your own words.
4. Mark the status: ⬜ not ready, 🔄 almost, ✅ I can explain it without notes.

| # | Question | Status |
|---|---|---|
| 1 | Comparable vs Comparator | ⬜ |
| 2 | Why `Arrays.sort(int[])` has no Comparator | ⬜ |
| 3 | Shallow copy vs deep copy | ⬜ |
| 4 | Why `binarySearch` needs a sorted array | ⬜ |
| 5 | Why `return a - b` is a bug | ⬜ |
| 6 | Is `Arrays.sort` stable? | ⬜ |
| 7 | `==`, `equals`, `Arrays.equals`, `deepEquals` | ⬜ |
| 8 | `clone()` vs `copyOf` vs `System.arraycopy` | ⬜ |
| 9 | What does `Arrays.asList` really return? | ⬜ |
| 10 | Array covariance and `ArrayStoreException` | ⬜ |

---

## 1. What is the difference between `Comparable` and `Comparator`?

**Short answer**

`Comparable` defines the **natural order** inside the class, with `compareTo`. `Comparator` defines an **external order** in a separate object, with `compare`. A class has only one `Comparable` order, but you can create as many `Comparator`s as you need.

**Details**

| | `Comparable<T>` | `Comparator<T>` |
|---|---|---|
| Package | `java.lang` | `java.util` |
| Method | `compareTo(T other)` | `compare(T a, T b)` |
| Where | Inside the class | Outside the class |
| Number of orders | One | Many |
| Need to modify the class? | Yes | No |

**When to use which**

- Use `Comparable` when there is one obvious default order (`String`, `Integer`, `LocalDate`).
- Use `Comparator` when you need several orders, when the order depends on the situation, or when you cannot change the class.

**Example**

```java
record Student(int id, String name, double gpa) implements Comparable<Student> {
    @Override
    public int compareTo(Student other) {
        return Integer.compare(this.id, other.id);   // natural order: by id
    }
}

Comparator<Student> byGpaDesc = Comparator.comparingDouble(Student::gpa).reversed();
Comparator<Student> byName = Comparator.comparing(Student::name);
```

**Follow-up question:** Which design pattern is `Comparator`? → The **Strategy pattern**: the sorting rule is an object you pass in.

---

## 2. Why does `Arrays.sort(int[])` not accept a `Comparator`?

**Short answer**

`Comparator<T>` is generic, and generics work only with **objects**, not with primitives. `int` is not an object, so `Comparator<int>` is impossible.

**Details**

- `Arrays.sort(int[])` sorts by the natural numeric order using a specialized algorithm (dual-pivot quicksort).
- `Arrays.sort(T[], Comparator<? super T>)` exists only for object arrays.

**How to sort `int[]` in descending order**

```java
// Option 1: sort ascending, then reverse manually
int[] a = {3, 1, 2};
Arrays.sort(a);
// reverse with a loop...

// Option 2: use a stream with boxing
int[] desc = Arrays.stream(a)
        .boxed()
        .sorted(Comparator.reverseOrder())
        .mapToInt(Integer::intValue)
        .toArray();

// Option 3: use Integer[] directly
Integer[] boxed = {3, 1, 2};
Arrays.sort(boxed, Collections.reverseOrder());
```

**Trade-off:** boxing creates objects, so it uses more memory and is slower.

---

## 3. What is the difference between a shallow copy and a deep copy of an array?

**Short answer**

A **shallow copy** creates a new array but the elements are the **same references**. A **deep copy** also creates new copies of the objects inside.

**Details**

- `clone()`, `Arrays.copyOf`, `Arrays.copyOfRange` and `System.arraycopy` are all shallow.
- For an array of primitives, shallow and deep are the same, because the values themselves are copied.
- For an array of objects (or a 2D array), the copy shares the inner objects.

**Example**

```java
int[][] original = {{1, 2}, {3, 4}};
int[][] shallow = original.clone();
shallow[0][0] = 99;
System.out.println(original[0][0]);   // 99 → the inner array is shared!

// Deep copy
int[][] deep = new int[original.length][];
for (int i = 0; i < original.length; i++) {
    deep[i] = original[i].clone();
}
```

**Interview tip:** say clearly *"a 2D array is an array of arrays, so `clone()` copies only the outer array."*

---

## 4. Why does `binarySearch` need a sorted array, and what is its complexity?

**Short answer**

Binary search compares the target with the **middle** element and throws away half of the array each step. This only works if the array is sorted. Complexity: **O(log n)**.

**Details**

- On an unsorted array the result is **undefined**: no exception, just a wrong answer.
- If the value is found, it returns its index.
- If not found, it returns `-(insertion point) - 1`, so the result is **negative**.
- If the value appears several times, there is **no guarantee** about which index you get.
- With objects, the array must be sorted with the **same** ordering (natural order or the same `Comparator`) that you use in the search.

**Example**

```java
int[] a = {10, 20, 30, 40};
Arrays.binarySearch(a, 30);   //  2
Arrays.binarySearch(a, 25);   // -3  → insertion point 2 → -(2) - 1
Arrays.binarySearch(a, 5);    // -1  → insertion point 0 → -(0) - 1
Arrays.binarySearch(a, 50);   // -5  → insertion point 4 → -(4) - 1
```

**Why `-(insertion point) - 1`?** So that "not found" is always negative, even when the insertion point is 0. (If it returned `-insertion point`, index 0 would give `-0 = 0`, which looks like "found".)

---

## 5. Why can `return a - b` in `compareTo` be a bug?

**Short answer**

Subtracting two `int` values can **overflow**, and the sign of the result becomes wrong.

**Example**

```java
int a = Integer.MAX_VALUE;
int b = -1;
System.out.println(a - b);   // overflow → -2147483648 (negative!)
// compareTo says "a < b", but in reality a > b
```

**Correct version**

```java
@Override
public int compareTo(Student o) {
    return Integer.compare(this.id, o.id);
}
```

**Extra points**

- For `double`, use `Double.compare`. Subtraction does not work because `compare` must return an `int`, and casting a small difference like `0.5` to `int` gives `0`.
- The contract of `compareTo`: **antisymmetry**, **transitivity**, and (recommended) **consistency with `equals`**.

---

## 6. Is `Arrays.sort` stable? Why does it matter?

**Short answer**

For **objects**, yes: `Arrays.sort(Object[])` and `Arrays.sort(T[], Comparator)` are stable. For **primitives**, stability is not guaranteed, but it does not matter.

**What is stability?**

A stable sort keeps the original order of elements that are **equal** according to the comparison.

**Example**

Students sorted by GPA. Two students have the same GPA:

```
Before: Alice(3.5), Bob(3.8), Carol(3.5)
Stable sort by GPA → Alice(3.5), Carol(3.5), Bob(3.8)   ← Alice stays before Carol
```

**Why it matters for objects only**

- Two equal `int` values are completely identical, so you cannot see their order.
- Two equal-by-key objects can be different objects, so their order is visible.
- Stability allows **multi-step sorting**: sort by name first, then by GPA, and the GPA ties stay in name order.

**Implementation:** object sorting uses **TimSort** (a hybrid of merge sort and insertion sort). Primitive sorting uses **dual-pivot quicksort**.

---

## 7. What is the difference between `==`, `equals`, `Arrays.equals` and `Arrays.deepEquals`?

**Short answer**

| Expression | What it compares |
|---|---|
| `a == b` | References (same object in memory?) |
| `a.equals(b)` | For arrays: also references (arrays do not override `equals`) |
| `Arrays.equals(a, b)` | Content, one level (uses `equals` on elements) |
| `Arrays.deepEquals(a, b)` | Content, all levels (for nested arrays) |

**Example**

```java
int[] x = {1, 2, 3};
int[] y = {1, 2, 3};
int[][] m1 = {{1, 2}, {3}};
int[][] m2 = {{1, 2}, {3}};

x == y                       // false
x.equals(y)                  // false
Arrays.equals(x, y)          // true
Arrays.equals(m1, m2)        // false (inner arrays compared by reference)
Arrays.deepEquals(m1, m2)    // true
```

**Related methods**

- `Arrays.compare(a, b)`: lexicographic comparison, returns negative, zero, or positive.
- `Arrays.mismatch(a, b)`: index of the first difference, or `-1` if the arrays are equal.

---

## 8. What is the difference between `clone()`, `Arrays.copyOf` and `System.arraycopy`?

| | `clone()` | `Arrays.copyOf` | `System.arraycopy` |
|---|---|---|---|
| Creates a new array? | Yes | Yes | **No** (you must provide the destination) |
| Can change the length? | No | **Yes** (pads or truncates) | Depends on your destination |
| Copy a range? | No | `copyOfRange` | **Yes**, any range |
| Overlapping ranges? | n/a | n/a | **Yes**, works correctly in the same array |
| Depth | Shallow | Shallow | Shallow |

**When to use each**

- `clone()`: exact copy, short code.
- `Arrays.copyOf`: resize an array (this is how `ArrayList` grows internally).
- `System.arraycopy`: copy a part into an existing array, or shift elements inside the same array. It is a **native** method and very fast.

**Padding rule for `copyOf`:** new elements are `0`, `false`, or `null`.

---

## 9. What does `Arrays.asList` really return?

**Short answer**

A **fixed-size list backed by the array**. It is not a normal `ArrayList`.

**Behavior**

```java
String[] arr = {"a", "b", "c"};
List<String> list = Arrays.asList(arr);

list.set(0, "X");     // OK → arr[0] also becomes "X" (write-through)
arr[1] = "Y";         // list.get(1) is now "Y" too
list.add("d");        // UnsupportedOperationException
list.remove(0);       // UnsupportedOperationException
```

**Classic trap with primitives**

```java
int[] nums = {1, 2, 3};
List<int[]> list = Arrays.asList(nums);
System.out.println(list.size());   // 1, not 3!
```

`int[]` is a single object, so the list has one element: the array itself. Use `Integer[]` or `List.of(1, 2, 3)`.

**Alternatives**

- Mutable copy: `new ArrayList<>(Arrays.asList(arr))`
- Immutable list: `List.of(arr)` or `List.copyOf(...)` (no `null` allowed)

---

## 10. What is array covariance and what is `ArrayStoreException`?

**Short answer**

Arrays are **covariant**: if `Dog` extends `Animal`, then `Dog[]` is a subtype of `Animal[]`. This is checked at **runtime**, so the compiler allows code that fails later.

**Example**

```java
Object[] objects = new String[2];   // allowed: String[] is an Object[]
objects[0] = "hello";               // OK
objects[1] = 42;                    // compiles, but throws ArrayStoreException at runtime
```

**Why?** The real runtime type of the array is `String[]`, and it refuses an `Integer`.

**Comparison with generics**

```java
List<Object> list = new ArrayList<String>();   // does NOT compile
```

Generics are **invariant**, so the error is found at **compile time**. This is one reason why generic collections are safer than arrays.

**Remember:** arrays are *covariant and reified* (they know their type at runtime). Generics are *invariant and erased* (the type is removed after compilation).

---

## Bonus questions to prepare

1. How would you remove duplicates from an array?
2. How would you merge two sorted arrays in O(n + m)?
3. How can you reverse an array in place?
4. What is a jagged array? Give a use case.
5. What is the default value of each element in `new int[3]`, `new String[3]`, `new boolean[3]`?
6. Why is array access O(1)?
7. What is the maximum size of an array in Java?

## Vocabulary for this chapter

| Word | Meaning |
|---|---|
| natural order | the default ordering of a class |
| lexicographic | dictionary-style order, compared element by element |
| shallow copy | copy of the container, not of the content |
| deep copy | copy of the container and the content |
| stable sort | equal elements keep their original order |
| overflow | a number goes beyond the maximum of its type |
| covariance | `Dog[]` can be used where `Animal[]` is expected |
| write-through | a change in one place appears in the other |
| insertion point | the position where a missing value would be inserted |