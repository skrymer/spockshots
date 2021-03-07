<!-- PROJECT LOGO -->
<br />
<p align="center">
  <h3 align="center">Spock Shots</h3>

  <p align="center">
    Easily assert json using snapshots.  
  </p>
</p>

<!-- TABLE OF CONTENTS -->

## Table of Contents

* [About the Project](#about-the-project)
    * [Built With](#built-with)
* [Getting Started](#getting-started)
* [Usage](#usage)
* [Roadmap](#roadmap)
* [Contact](#contact)
* [Acknowledgements](#acknowledgements)

<!-- ABOUT THE PROJECT -->

## About The Project

Adds json snapshot testing to spock and spring mockmvc tests. Allows assertion of json content using a snapshot file.

### Built With

* [Groovy](https://getbootstrap.com)
* [Spock](https://jquery.com)
* [Json-unit](https://github.com/lukas-krecan/JsonUnit)
* [hamcrest](http://hamcrest.org/)

<!-- GETTING STARTED -->

## Getting Started

Get source by cloning repository:
```shell
git clone https://github.com/skrymer/spockshots.git
```

<!-- USAGE EXAMPLES -->

## Usage

Use in combination with MockMvc:

```groovy
@Spockshot(
        rootFolder = "./src/test/groovy" #1
)
@WebMvcTest()
class ExampleSpec extends Specification {
    @Autowired
    MockMvc mockMvc

    @Snapshot(name = 'snapshot') #2 
    def 'example feature'() {
        when: 'getting data'
        def response = mockMvc.perform(get("/api/example"))

        then:
        'data matches snapshot'
        response.andExpect(matchesSnapshot()) #3
    }
}
```
1. @Spockshot is optional, but will allow overriding of test root folder
2. Use @Snapshot to specify the snapshot name
3. Check that response matches snapshot

<!-- ROADMAP -->

## Roadmap

Move lib to maven repository

See the [open issues](https://github.com/skrymer/spockshots/issues) for a list of proposed features (and known issues).

<!-- CONTACT -->

## Contact

Sonni Nielsen - [@sonni_nielsen](https://twitter.com/sonni_nielsen)

Project Link: [https://github.com/skrymer/spockshots](https://github.com/skrymer/spockshots)

<!-- ACKNOWLEDGEMENTS -->

## Acknowledgements

* [Json unit](https://github.com/lukas-krecan/JsonUnit)