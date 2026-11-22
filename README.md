# major 
- removed the deprecated ktFileManager, no longer supported
- removed the deprecated ktColor, no longer supported, use android's **Color** classs
- updated the useState, it combines both useState and useEffect of [reactjs](https://nodejs.org)
  - useState not outputs a set  not a method [see](docs/files/useState.md) or [home](docs/home.md)
    ```kotlin
     val (variable,onMethod) = useState<T>
    ```
# minor
- added a documentation [see](docs/home.md)
- deprecated the old ktVibrator,  use ktVibration instead.
- deprecated the ktSelectorm  use getViewByTag/Id, [see](docs/files/ktComponents.md)
  