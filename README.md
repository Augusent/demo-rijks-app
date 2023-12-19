### Description
Rijks museum app featuring art collection and art details screens.

### Architecture overview

#### Build Script
- The project uses Gradle's [convention plugins](https://docs.gradle.org/current/samples/sample_convention_plugins.html) defined in `/gradle/build-logic` dir for sharing build logic between submodules.
- Build Script type safety is achieved by using Gradle's [Version Catalogue](https://docs.gradle.org/current/userguide/platforms.html) and Kotlin DSL.

#### Module Organization
- The project's architecture follows a multi-module approach with a horizontal module separation strategy for `:feature` modules such as `:feature:art-gallery` and `:feature:art-details`.
- Vertically, these 'feature' modules rely on 'library' and 'domain' modules that maintain public/impl contract separation approach. For example, the `:api:public` module defines the server API contract, while `:api:rijks-impl` implements it.

#### :app Module
- The `:app` module serves as the main entry point, which constructs the project's dependency graph using `Hilt` and navigation graph using `androidx.navigation`.
- It aggregates all horizontal features in the project and their associated dependencies.

#### Presentation Layer
- In the presentation layer, the project follows MVVM approach and relies on `Pager` implementation from `androidx.paging` for pagination
- As the project is based on pure Compose for UI implementation, the app handles configuration changes w/o activity recreation

#### Testing
- Sample tests consist of:
  * `ArtCollectionRepositoryTest.kt` in `:data` unit test testing repository and its pagination source
  * `ArtGalleryWorkflowTest.kt` in `:feature:art-gallery` integration test testing presentation logic of a paginated data source
  * `AppIntegrationInstrumentedTest.kt` in `:app` instrumented UI integration (e2e against a mock server) test with OkHttps's `MockWebServer` challenging app's UI, navigation, network client (serialization and data contract), and overall user workflow.
- Tasks to run unit and instrumented tests are: `./gradlew testDebugUnitTest` and `./gradlew :app:connectedDebugAndroidTest`
- Test fixtures are available as a separate `:data:testFixtures` module dependency for utilities and fakes reuse

#### Notes
The project requires Java 17 installed to build properly.

#### Recordings
| Android                       |
|-------------------------------|
| ![](recordings/rijks-app.gif) |