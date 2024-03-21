# E) Confirm that Sonar analysis was executed. Access the SonarQube dashboard (default :http://127.0.0.1:9000). Has your project passed the defined quality gate? Elaborate your answer.

According to the SonarQube dashboard, the project has passed the defined quality gate with the following metrics:
- Security Rating: A (0 open issues)
- Reliability Rating: A (0 open issues)
- Maintainability Rating: A (18 open issues)
- Coverage: 79.7%
- Duplications: 0.0%
- Security Hotspots: 1

The current 18 open issues are all code smells related to maintainability, and where 1 issue is a consistency issue and the rest are intentionality issues.

# f) Explore the analysis results and complete with a few sample issues, as applicable.

| Issue | Problem Description | How to solve | 
| --- | --- | --- |
| Code Smell (Minor) | The return type of this method should be an interface such as "List" rather than the implementation "ArrayList". | Change the return type of the method to the interface type in the `findMatchesFor` function of `EuromillionsDraw.java` file. |
| Code Smell (Major) (x3) | Invoke method(s) only conditionally. | Add a Log Flag and wrap all log statements in a condition to check if the log flag is enabled in the `log` function of `DemoMain.java` file. |
| Code Smell (Minor) | Remove this unused import 'java.security.NoSuchAlgorithmException'. | Remove the unused import in the `java.security.NoSuchAlgorithmException` of `Dip.java` file. |
| Code Smell (Minor) | Remove this unused import 'java.security.SecureRandom'. | Remove the unused import in the `java.security.SecureRandom` of `Dip.java` file. |
| Code Smell (Major) (x2) | Refactor the code in order to not assign to this loop counter from within the loop body. | Move the `i++` increment to the `for` loop declaration in the `generateRandomDip` function of `Dip.java` file. |
| Code Smell (Minor) | Reorder the modifiers to comply with the Java Language Specification. | Change `static public EuromillionsDraw generateRandomDraw(...)` to `public static EuromillionsDraw generateRandomDraw(...)`|
| Code Smell (Minor) | Replace the type specification in this constructor call with the diamond operator ("<>"). | Change `ArrayList<Dip> results = new ArrayList<Dip>();` to `ArrayList<Dip> results = new ArrayList<>();`|
| Code Smell (Minor) (x4) | Remove this 'public' modifier. | Remove all `public` assignements of classes in `DipTest.java` (JUnit5 test classes and methods should have default package visibility)|
| Code Smell (Minor) (x2) | Remove this 'public' modifier. | Remove all `public` assignements of classes in `EuromillionsDrawTest.java` (JUnit5 test classes and methods should have default package visibility)|
| Code Smell (Major) (x2) | Use assertNotEquals instead. | Change `assertFalse(setA.equals(null))` to `assertNotEquals(null, setA)` in `BoundedSetOfNaturalsTest.java` file. |
| Security Hotspot | Make sure that using this pseudorandom number generator is safe here. | Manually review if the `Random()` usage doesnt cause any security issues in the `Dip.java` file. |