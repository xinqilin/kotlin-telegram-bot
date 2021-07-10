## Kotlin Guess Number Game

``2021/06/20 14:00 跟著 b2etw Vincent 大實作``

### Dependency 

- webflux

### TG Bot /getUpdate Json content

```JSON
{
    "ok": true,
    "result": [
        {
            "update_id": 732391106,
            "message": {
                "message_id": 26,
                "from": {
                    "id": 931004342,
                    "is_bot": false,
                    "first_name": "Lin",
                    "last_name": "Bill",
                    "username": "Bill",
                    "language_code": "zh-hans"
                },
                "chat": {
                    "id": 931004342,
                    "first_name": "Lin",
                    "last_name": "Bill",
                    "username": "Bill",
                    "type": "private"
                },
                "date": 1624199117,
                "text": "12131"
            }
        }
    ]
}
```

### Test

```kotlin

    @Test
    internal fun test() {
        //測試3A
        //arrange
        val guessNumberGame = GuessNumberGame()

        //act
        val result = guessNumberGame.check("")

        //assert
        Assertions.assertThat(result).isEqualTo("")
    }

```

### java version error

```
版本太高要降版

Caused by: java.lang.UnsupportedClassVersionError: com/bill/guessgame/domain/GuessNumberGameTests has been compiled by a more recent version of the Java Runtime (class file version 55.0), 
this version of the Java Runtime only recognizes class file versions up to 52.0

45 = Java 1.1
46 = Java 1.2
47 = Java 1.3
48 = Java 1.4
49 = Java 5
50 = Java 6
51 = Java 7
52 = Java 8
53 = Java 9
54 = Java 10
55 = Java 11
56 = Java 12
57 = Java 13

```

### build docker image

- gradlew clean -> gradle folder is empty
- gradlew build -> build success
- gradlew bootRun
- gradlew bootJar
- java -jar build/libs/xxx.jar  -> another way to run app
- gradlew bootbuildimage -> need connect to docker
- Successfully built image 'docker.io/library/guessgame:0.0.1-SNAPSHOT'
- docker run docker.io/library/guessgame:0.0.1-SNAPSHOT



