# ktDynClass() and ktDynFunc()

[<-- Back](../home.md)

`ktDynClass()` and `ktDynFunc()` allow you to access classes, create objects, and call methods or functions dynamically using their class names as strings.

They use Java Reflection to discover classes and execute their constructors, methods, and functions at runtime.

This means you do not need to import or directly reference a class when accessing it.

## Methods

### 1. ktDynClass()

```kotlin
fun ktDynClass(className: String): DynClass
```

Finds a class using its fully qualified class name and returns a `DynClass` object.

**Parameter:**
- `className` – The full name of the class, including its package.

**Example:**

```kotlin
val person = ktDynClass("com.example.Person")
```

### 2. DynClass

Represents a dynamically discovered class.

**get(methodName)**

```kotlin
operator fun get(methodName: String): DynMethod
```

Finds a method by name and returns a `DynMethod` that can be called later.

**Example:**

```kotlin
val person = ktDynClass("com.example.Person")

val sayHello = person["sayHello"]
```

**invoke(args)**

```kotlin
operator fun invoke(vararg args: Any?): DynObject
```

Creates an instance of the class by finding a matching constructor.

**Example:**

```kotlin
val person = ktDynClass("com.example.Person")

val obj = person("Enock", 25)
```

The arguments must match an available public constructor.

### 3. DynObject

Represents an object created dynamically from a class.

**get(methodName)**

```kotlin
operator fun get(methodName: String): DynMethod
```

Finds a method belonging to the object's class.

**Example:**

```kotlin
val person = ktDynClass("com.example.Person")("Enock", 25)

val sayHello = person["sayHello"]
```

### 4. DynMethod

Represents a dynamically discovered method.

**invoke(args)**

```kotlin
operator fun invoke(vararg args: Any?): Any?
```

Executes the selected method using the supplied arguments and returns its result.

It supports both static methods and instance methods.

**Example:**

```kotlin
val person = ktDynClass("com.example.Person")("Enock", 25)

person["sayHello"]()

val result = person["add"](10, 20)
```

For static methods, an object instance is not required.

### 5. ktDynFunc()

```kotlin
fun ktDynFunc(
    className: String,
    functionName: String
): DynFunction
```

Finds a static function using its class name and function name.

This is useful when accessing top-level Kotlin functions compiled into JVM classes.

**Parameters:**
- `className` – The fully qualified JVM class name containing the function.
- `functionName` – The name of the function.

**Example:**

```kotlin
val add = ktDynFunc("com.example.UtilsKt", "add")

val result = add(10, 20)
```

### 6. DynFunction

Represents a dynamically discovered static function.

**invoke(args)**

```kotlin
operator fun invoke(vararg args: Any?): Any?
```

Executes the function with the supplied arguments and returns its result.

**Example:**

```kotlin
val multiply = ktDynFunc("com.example.UtilsKt", "multiply")

val result = multiply(5, 4)
```

## Examples

### Accessing a class dynamically

```kotlin
val person = ktDynClass("com.example.Person")
```

### Creating an object

```kotlin
val person = ktDynClass("com.example.Person")

val obj = person("Enock", 25)
```

### Calling an object method

```kotlin
val person = ktDynClass("com.example.Person")("Enock", 25)

person["sayHello"]()
```

### Calling a method with arguments

```kotlin
val person = ktDynClass("com.example.Person")("Enock", 25)

val result = person["add"](10, 20)
```

### Calling a static method

```kotlin
val utils = ktDynClass("com.example.Utils")

val result = utils["calculate"](10, 20)
```

### Calling a top-level Kotlin function

```kotlin
val add = ktDynFunc("com.example.UtilsKt", "add")

val result = add(10, 20)
```

## Notes

- These functions use Java Reflection to access classes and methods at runtime.
- Class names must be fully qualified, including the package name.
- `ktDynClass()` can access public constructors and methods.
- `ktDynFunc()` is intended for static functions.
- Arguments are checked against method or constructor parameter types before execution.
- Primitive Java types are converted to their corresponding wrapper types during argument matching.
- A `null` argument is accepted during matching, but the actual parameter must allow null.
- If no matching constructor or method is found, an error is thrown.
- Instance methods require an object instance, while static methods do not.
- Reflection can fail when a class or method is unavailable, inaccessible, or has been removed or renamed.