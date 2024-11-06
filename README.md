# Milestone Project Quality Assurance #2

## :white_check_mark: Prequisites

- [Docker](https://www.docker.com/)
- [Android Emulator](https://developer.android.com/studio)

### :white_check_mark: Steps

1. Run android emulator and adjust the mobile base steps on the `mobile\src\test\java\utils\BaseTest.java` to accomodate the emulator
2. Run the docker compose by running `docker-compose up -d`
3. Open the browser and navigate to http://localhost:8080/jenkins
4. Login with username: `admin` and password: `admin`
5. Click on the `Build Now`