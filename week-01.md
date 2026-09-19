---
layout: lesson
title: Foundations + Strategy
description: Learn the language of object-oriented design, then use your first pattern to make a small Java application easier to change.
permalink: /lessons/week-01/
week: 1
category_label: Foundations · Behavioral patterns
pattern_count: 1
patterns: Strategy
sections:
  - { id: learning-goals, title: Learning goals }
  - { id: what-is-a-pattern, title: What is a pattern? }
  - { id: java-toolkit, title: Your Java toolkit }
  - { id: oop-foundations, title: OOP foundations }
  - { id: design-principles, title: Design principles }
  - { id: solid, title: SOLID principles }
  - { id: strategy, title: The Strategy pattern }
  - { id: guided-lab, title: Guided Java lab }
  - { id: check-understanding, title: Check your understanding }
  - { id: further-reading, title: Further reading }
---

## Learning goals
{: #learning-goals }

By the end of this lesson, you should be able to:

- Describe a design pattern as a solution to a recurring problem in a context.
- Distinguish creational, structural, and behavioral patterns.
- Compile and run a small Java program and explain the roles of the JDK and JVM.
- Explain encapsulation, abstraction, inheritance, polymorphism, and composition.
- Recognize the intent of each SOLID principle in a small design.
- Implement Strategy with an interface, two implementations, and constructor injection.
- Test behavior and explain whether the extra abstraction is justified.

**Before class:** review variables, conditionals, methods, and simple data structures. Have a JDK and an editor ready. This is a design course with a Java refresher, so ask for help early if the programming basics are unfamiliar.

**Session route:** begin with patterns and Java, establish the OOP vocabulary, introduce design principles, then spend the practical part of the session implementing and testing Strategy. SOLID will return throughout the course; this week establishes its meaning.

## What is a design pattern?
{: #what-is-a-pattern }

Suppose a campus bookshop calculates a total differently for regular customers and students. The first version uses an `if` statement. Later, staff pricing and a seasonal promotion arrive. The design question becomes: **how can pricing rules change without repeatedly rewriting checkout?**

A design pattern captures a reusable way to organize responsibilities for a recurring problem. It names the problem, the context, the cooperating objects, and the consequences of the solution. The same pattern can appear in very different applications.

A pattern is not a finished library or a code template that must be followed literally. It is also not an algorithm: an algorithm specifies a procedure for computing a result, while a design pattern describes how responsibilities and collaboration can be organized. Strategy, for example, lets a client use alternative algorithms through the same contract.

### The three categories

| Category | What varies? | Example |
| --- | --- | --- |
| **Creational** | How objects are constructed or obtained. | Factory Method lets a subclass supply a product used by an inherited operation. |
| **Structural** | How objects and classes are connected. | Adapter lets a client use an object through a different interface. |
| **Behavioral** | How behavior and communication are organized. | Strategy lets a client delegate an algorithm to an interchangeable collaborator. |

The **Gang of Four**, or **GoF**, refers to Erich Gamma, Richard Helm, Ralph Johnson, and John Vlissides, the authors of *Design Patterns: Elements of Reusable Object-Oriented Software*. Their catalog contains 23 patterns: five creational, seven structural, and eleven behavioral. These are the patterns taught in this course; they are not every pattern that exists in software engineering.

Patterns also have a **scope**. Class patterns rely primarily on inheritance; object patterns rely primarily on collaborating objects. This is a separate dimension from purpose. Strategy is an object behavioral pattern. Next week's Template Method is a class behavioral pattern.

### How to study a pattern

For every pattern, ask: What problem does it solve? What is expected to change? Which objects have which responsibilities? What becomes easier? What becomes more complicated? What simpler alternative could work?

> A pattern earns its place by solving a problem. A small conditional can be a better design than several classes when the behavior is simple and stable.

## Your Java toolkit
{: #java-toolkit }

We use Java because its classes, interfaces, access controls, and static types make relationships between objects explicit. We start with plain Java, without an application framework.

- The **JDK** provides development tools, including the `javac` compiler and the `java` launcher.
- `javac` translates `.java` source into `.class` bytecode.
- The **JVM** executes that bytecode. Java passes arguments by value, including the values of references to objects.

The examples in this course target **Java 21 or newer**, without preview features. Use a full JDK, not only a runtime. An IDE is convenient, but the examples also run in a terminal. See the official [Java getting-started guide](https://dev.java/learn/getting-started/) for installation guidance.

### Compile your first program

Check that both commands are available:

```shell
java --version
javac --version
```

Save the following as `HelloDesign.java`. A public top-level class must have the same name as its source file.

{% highlight java %}
{% include_relative examples/week-01/HelloDesign.java %}
{% endhighlight %}

From the directory containing the file, run:

```shell
javac HelloDesign.java
java HelloDesign
```

You should see `Hello, design patterns!`. `main` is the entry point, `static` means the method belongs to the class, and `void` means it returns no value. `String[] args` holds command-line arguments. Semicolons end statements and braces group their bodies.

### Java vocabulary we will use

| Construct | Meaning in this course |
| --- | --- |
| Class and object | A class defines a type and its implementation; an object is an instance with its own state. |
| Constructor and `new` | A constructor initializes an instance; `new` creates it. Constructors have no return type. |
| Field and method | A field stores state; a method provides behavior. |
| `private`, `public`, `protected` | Access controls: class-internal, publicly accessible, and package/subclass access respectively. No modifier usually means package access. |
| `interface` and `implements` | Declare a contract and a class that fulfills it. |
| `abstract class` and `extends` | An abstract class can share state and implemented methods while leaving some methods abstract. A class can extend one direct superclass. |
| `final` | A variable cannot be reassigned, a method cannot be overridden, or a class cannot be subclassed. A final reference does not make its object immutable. |
| Package and `import` | Packages organize types; imports allow short names for types in other packages. |
| Exception | Signals a failure; for example, an invalid argument can cause `IllegalArgumentException`. |

## OOP foundations
{: #oop-foundations }

Objects combine state and behavior, but good object-oriented design also gives them clear responsibilities. Start by asking what an object knows, what it does, and what it delegates.

### Encapsulation: protect valid state

Encapsulation keeps representation details behind a controlled boundary. Making fields private is a starting point; the public operations must also preserve the object's rules. A checkout should reject a negative subtotal rather than allow an invalid calculation to spread through the application.

A getter for every field is not automatically good encapsulation. Returning a mutable internal collection, for example, may let callers bypass the object's validation.

### Abstraction: expose the useful contract

Abstraction exposes the capabilities a client needs while hiding unnecessary details. A `PricingStrategy` promises to calculate a price. Checkout does not need to know the formula or whether the rule is for a student or a regular customer.

An **interface** expresses a capability that unrelated classes can implement. An **abstract class** is useful when subclasses need shared implementation or state. Neither is always preferable: choose based on the relationship and the responsibilities.

### Inheritance: specialize a type

Inheritance creates a subtype relationship. Java uses `extends` for class inheritance and `implements` for implementing an interface. Overriding supplies a subtype's implementation of an inherited method; overloading instead defines methods with different parameter lists.

Use inheritance when the subtype can honor the parent's contract. Sharing a few lines of code alone is a weak reason to create a hierarchy. In Week 2, Template Method will show a deliberate use of inheritance to customize algorithm steps.

### Polymorphism: one contract, different behavior

An interface reference can refer to different implementations. A call to an overridden instance method uses the implementation belonging to the actual object:

```java
PricingStrategy pricing = new StudentPricing();
int total = pricing.priceInCents(10_000);
```

This fragment uses the classes in the complete example below. The variable's declared type is `PricingStrategy`; the actual object is a `StudentPricing`. The client can call the contract without checking the object's concrete type.

### Composition and delegation: collaborate with another object

With composition, an object is built using other objects. With delegation, it asks a collaborator to perform part of its work. `Checkout` holds a `PricingStrategy` reference and delegates the pricing calculation to it.

**Favor composition over inheritance** means considering collaborators before growing a subclass hierarchy. It does not prohibit inheritance. Composition adds its own wiring and indirection, so it should still solve a concrete problem.

In UML, an association shows that objects are connected. A strong ownership relationship with a dependent lifetime is shown as UML composition with a filled diamond. The broader programming phrase “object composition” also includes collaborators that may be shared; a field does not automatically imply UML composition.

## Design principles to keep nearby
{: #design-principles }

| Principle | Practical question |
| --- | --- |
| **High cohesion** | Do this class's responsibilities belong together? Pricing and sending email probably change for different reasons. |
| **Low coupling** | How much does this class know about its collaborators' details? Prefer a small stable contract over access to their internals. |
| **Separation of concerns** | Can input handling, pricing, and presentation change independently? |
| **Program to an interface** | Can the client depend on the required capability instead of a specific implementation? This is a design choice, not a demand for an interface for every class. |
| **Encapsulate what varies** | Can a changing pricing rule be isolated from stable checkout behavior? |
| **DRY — Don't Repeat Yourself** | Is the same business rule maintained in several places? Similar-looking code is not always the same knowledge. |
| **KISS — Keep It Simple** | Is the abstraction easier to understand than the problem it solves? |
| **YAGNI — You Aren't Gonna Need It** | Does a current requirement justify this flexibility, or are we building for an imagined future? |

These principles require judgment. Reducing duplication through a large, highly configurable abstraction may damage readability and cohesion. Explain the trade-off rather than treating a principle as an absolute rule.

## SOLID: five questions about a design
{: #solid }

### S — Single Responsibility Principle

A module should have one coherent reason to change. A class that calculates prices, writes database records, and formats receipts has several unrelated responsibilities. Splitting the changing pricing policy from checkout makes the boundary clearer. SRP does not mean “one method per class.”

### O — Open/Closed Principle

Design a stable part so a known kind of variation can be added without editing that part. Adding `StaffPricing` should not require changing `Checkout`. The code that assembles the application will still need to select the new strategy. Open/closed does not mean that no existing code may ever change.

### L — Liskov Substitution Principle

A subtype should preserve the expectations of clients using the base contract. For our pricing interface, every strategy accepts any non-negative subtotal and returns a price between zero and that subtotal. A student strategy that unexpectedly rejects small valid purchases or returns a negative price would violate that contract, even if Java accepts its types.

### I — Interface Segregation Principle

Clients should not depend on operations they do not need. A pricing interface should calculate a price; it should not also require implementations to print receipts and send notifications. Small interfaces should describe cohesive capabilities, not arbitrary fragments.

### D — Dependency Inversion Principle

High-level policy should depend on abstractions rather than concrete low-level details. `Checkout` uses `PricingStrategy`; the concrete policies implement that abstraction. It does not construct a specific `StudentPricing` internally.

**Dependency injection** is a technique: a caller supplies an object's dependencies, for example through its constructor. **Dependency inversion** is a design principle about the direction of dependencies. Constructor injection helps this example follow the principle, but the two terms are not synonyms. No framework is needed.

## Your first pattern: Strategy
{: #strategy }

**Intent:** put alternative algorithms behind a common interface so a client can delegate to an appropriate implementation.

**Category:** behavioral. **Scope:** object.

### Start with the simple version

This method is a reasonable starting point when there are only two stable pricing rules:

```java
static int totalInCents(int subtotalCents, boolean student) {
    if (subtotalCents < 0) {
        throw new IllegalArgumentException("Subtotal must be non-negative");
    }
    if (student) {
        return subtotalCents - subtotalCents / 5;
    }
    return subtotalCents;
}
```

Now suppose the bookshop needs additional policies and each policy will evolve independently. A boolean no longer expresses the choice well. Strategy isolates that variation: checkout validates its input, and the selected policy calculates the result.

### Identify the roles

| Role | Java type | Responsibility |
| --- | --- | --- |
| Context | `Checkout` | Uses a pricing policy without knowing its concrete formula. |
| Strategy | `PricingStrategy` | Defines the common operation and behavioral contract. |
| Concrete strategies | `RegularPricing`, `StudentPricing` | Implement different pricing algorithms. |
| Client / assembly code | `StrategyDemo.main` | Chooses a policy and provides it to checkout. |

<div class="my-8 rounded-lg border border-stone-200 p-5 text-center text-sm" role="img" aria-label="Checkout holds a reference to PricingStrategy. RegularPricing and StudentPricing implement PricingStrategy.">
  <div class="flex flex-wrap items-center justify-center gap-3 font-mono">
    <div class="rounded border border-stone-200 bg-stone-50 px-4 py-2">Checkout<span class="block font-sans text-xs text-stone-500">context</span></div>
    <i class="fa-solid fa-arrow-right text-stone-400" aria-hidden="true"></i>
    <div class="rounded border border-stone-200 bg-stone-50 px-4 py-2">PricingStrategy<span class="block font-sans text-xs text-stone-500">interface</span></div>
  </div>
  <div class="mt-3 text-stone-500">Implemented by RegularPricing and StudentPricing</div>
</div>

In a UML class diagram, a solid line can show `Checkout`'s association with `PricingStrategy`. A dashed line with a hollow triangle points from each implementation to the interface it realizes. An inheritance relationship uses a solid line with a hollow triangle pointing to the superclass. The simplified sketch above emphasizes collaboration rather than full UML notation.

### Complete Java implementation

Save this as `StrategyDemo.java`, or [download the source]({{ '/examples/week-01/StrategyDemo.java' | relative_url }}). The helper types have package access so they can live in the same file as the one public class. In a larger project, they would normally have separate source files and a named package.

{% highlight java %}
{% include_relative examples/week-01/StrategyDemo.java %}
{% endhighlight %}

The example stores money in **integer cents**, avoiding binary floating-point rounding. Our specific rule is to round the **discount** down to a whole cent: on 999 cents the discount is 199 cents, so the final price is 800 cents. This is a teaching example of an explicit contract, not a universal financial rounding rule.

`@Override` asks the compiler to check that the method implements or overrides another declaration. `Objects.requireNonNull` rejects missing collaborators. The `private final` field prevents clients from replacing checkout's strategy after construction. Strategy does not require a runtime setter; choosing a policy when creating the context is enough.

The context enforces the non-negative input precondition before delegating. Each strategy must honor the documented output contract. Java's type system does not enforce those numeric rules, so tests matter.

### Trace a call

1. `main` constructs a `StudentPricing` and passes it into `Checkout`.
2. The checkout stores that reference under the interface type.
3. `totalInCents(10_000)` checks the subtotal and calls the policy.
4. Java dispatches the call to `StudentPricing.priceInCents`.
5. The policy returns 8,000 cents, which checkout returns to the caller.

### Benefits, costs, and boundaries

- **Benefit:** new policies can be added without editing the context's pricing logic.
- **Benefit:** each policy can be tested independently, and the context can be tested with a supplied collaborator.
- **Cost:** there are more types, and assembly code must choose a policy.
- **Boundary:** Strategy does not discover the right policy automatically. A conditional may still be appropriate at the application boundary where the choice is made.
- **When to keep it simple:** if one small calculation is unlikely to vary, a method or conditional can be clearer.

An interface with one abstract method can later be implemented with a lambda. We use named classes first to make the pattern's roles visible.

## Guided Java lab
{: #guided-lab }

### 1. Run the example

Download [StrategyDemo.java]({{ '/examples/week-01/StrategyDemo.java' | relative_url }}) and [StrategyDemoTest.java]({{ '/examples/week-01/StrategyDemoTest.java' | relative_url }}) into the same directory, or find them in `examples/week-01` in the course repository. Open a terminal in that directory:

```shell
javac StrategyDemo.java StrategyDemoTest.java
java StrategyDemo
java StrategyDemoTest
```

Expected output from the demo:

```text
Regular: 10000 cents
Student: 8000 cents
```

The test runner prints `All 10 checks passed.` It uses ordinary Java and throws `AssertionError` on failure, so no testing library or `-ea` flag is required. Later lessons can use a test framework; here the focus is the behavior being checked.

### 2. Read a behavior check

```java
Checkout student = new Checkout(new StudentPricing());
int actual = student.totalInCents(10_000);
if (actual != 8_000) {
    throw new AssertionError("Expected 8000 cents, got " + actual);
}
```

This follows **arrange, act, assert**: prepare the objects, perform an operation, check the observable result. Test the contract rather than the names of private fields or how many internal calls occur.

The supplied runner checks ordinary prices, zero, whole-cent rounding, a large subtotal, invalid input, a missing strategy, and a separately supplied third policy. Its last check uses an anonymous implementation: a class implementation declared directly where its object is created.

### 3. Add a new policy

Create `StaffPricing` implementing `PricingStrategy`. It gives a 10% discount, rounding the discount down to a whole cent. Add it as a separate file beside the example. Leave `Checkout`, `RegularPricing`, and `StudentPricing` unchanged.

Construct a checkout with the new policy in `main`. Add behavior checks to the test runner for these cases:

| Subtotal | Expected staff total | Reason |
| --- | --- | --- |
| 10,000 cents | 9,000 cents | Standard 10% discount. |
| 0 cents | 0 cents | Empty purchase remains valid. |
| 999 cents | 900 cents | Discount is 99 whole cents. |
| −1 cent | `IllegalArgumentException` | The context rejects invalid input. |

Compile the additional class with the others:

```shell
javac StrategyDemo.java StaffPricing.java StrategyDemoTest.java
java StrategyDemoTest
```

If you add checks, update the test runner's success message to reflect the new count. Explain which code changed and why the context did not need to change.

### 4. Explain the design

Sketch the context, interface, and three implementations. Mark the interface-realization relationships and the object references. Identify one benefit and one cost compared with the original conditional. Explain where the choice of pricing policy belongs.

### 5. Save your work with Git

In your own exercise repository, inspect changes with `git status`, stage the `.java` source files, and make a commit such as `Implement interchangeable pricing policies`. Keep generated `.class` files out of version control. Include brief instructions showing how to compile and run your example.

**Completion check:** you can run the program, explain every collaborator, add a policy without changing checkout, and justify when you would keep the simpler conditional.

## Check your understanding
{: #check-understanding }

Try answering before opening the discussion notes.

<details markdown="1">
<summary>Why is Strategy behavioral rather than creational?</summary>

Its purpose is to organize interchangeable algorithms. Although the client constructs a strategy object, the design problem is about choosing behavior, not providing a construction mechanism.

</details>

<details markdown="1">
<summary>Does an interface guarantee Liskov substitution?</summary>

No. The compiler checks compatible method declarations. Implementations must also honor behavioral expectations such as accepted inputs and valid outputs. A strategy returning a negative total violates this lesson's contract.

</details>

<details markdown="1">
<summary>Is constructor injection the same as dependency inversion?</summary>

No. Injection describes how a dependency is supplied. Inversion describes how code depends on abstractions. Passing a concrete `StudentPricing` into a constructor typed specifically as `StudentPricing` is injection, but it does not provide the same abstraction boundary as accepting `PricingStrategy`.

</details>

<details markdown="1">
<summary>Why can adding a strategy still require editing main?</summary>

The application must assemble and select its collaborators somewhere. The stable part here is checkout's use of the pricing contract. Open/closed is applied to a specific variation boundary, not to every line in the application.

</details>

<details markdown="1">
<summary>When would you avoid Strategy?</summary>

When there is only a small, stable calculation and no meaningful need for interchangeable behavior. More classes and indirection can cost more than the flexibility is worth.

</details>

## Further reading
{: #further-reading }

- [Getting started with Java](https://dev.java/learn/getting-started/) — the official guide to the tools and your first program.
- [Objects, classes, interfaces, packages, and inheritance](https://dev.java/learn/oop/) — Java's official introduction to the OOP vocabulary.
- [Interfaces in Java](https://dev.java/learn/interfaces/) — a closer look at interface contracts and implementations.
- Erich Gamma, Richard Helm, Ralph Johnson, and John Vlissides, *Design Patterns: Elements of Reusable Object-Oriented Software* — the introduction and Strategy chapter.

Next week, **Template Method** will keep an algorithm's overall structure in a base class while subclasses customize selected steps. Compare that approach with the collaborator-based design you built today.
