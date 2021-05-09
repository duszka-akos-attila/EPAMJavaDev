# EPAMJavaDev
Project for EPAM Java Development course.

## DevLog:

---
### Work Table

| Finish date | Job | Description | Time taken |
| -- | -- | -- | -- |
| 2021.05.08. | Initialization | Creating Git repository with necessary files like .gitignore, README.md, etc... |
| 2021.05.08. | Project setup | Forked the project frame from epam-deik-cooperation/epam-deik-java-dev, and made some changes in configuration files. |
| 2021.05.09. 01:00 | CLI | Finished the frame of the CLI. | 1h |
| 2021.05.09. 01:38 | Domains | Finished the domains of Movie, Room, User and Screening. | 37m |
| 2021.05.09. 02:08 | Repositories | Finished the repositories of Movie, Room, User and Screening without implementation. | 30m |
| 2021.05.09. 03:18 | Projections | Finished the projections of Movie, Room, User and Screening. | 70m |
| 2021.05.09. 05:40 | DAOs | Finished the DAOs for Movie, Room, User and Screening. It might need some fixes later! | 82m |
| 2021.05.09. 06:00 | Configuration | Preparing for the database connection. | 20m |
| 2021.05.09. 23:05 | Bugfix | Fixing some configurations for the database connection. | 30m |
---

### Interesting

First command implemented at 05.09. 00:00.

![First command](IMGs/first_command.png)

---

### Structuring the data and functionalities by the requirements

#### Data Structures

##### Movie

| Variable | Type | Name | Description |
| -- | -- | -- | -- |
| ID | `long` | `movieId` | The identifier of the movie. **Identifies the movie in the database.** |
| Title | `String` | `movieTitle` | The title of the movie. **Identifies the movie in the application.** |
| Genre | `String` | `movieGenre` | The genre of the movie. |
| Length | `int` | `movieLength` | The length of the movie in minutes. |

###### Commands

| Name | Syntax | Privileged command | Description |
| -- | -- | -- | -- |
| Create movie | `create movie <movie title> <genre> <screen time in minutes>` | [x] | If a movie not exists with the given title, creates it with the given parameters. |
| List movies | `list movies` | [] | Lists all movies from the database. |
| Update movie | `update movie <movie title> <genre> <screen time in minutes>` | [x] | If a movie exists with the given title, it's genre and screen time is changes with the given parameters. |
| Delete movie | `delete movie <movie title>` | [x] | If a movie exists with the given title, deletes it. |

####### Outputs

| Command | Requirement(s) | Requirement satisfied | Output |
| -- | -- | -- | -- |
| List movies | There is at least one movie in the database | [] | `There are no movies at the moment` |
| List movies | There is at least one movie in the database | [x] | All the screenings from the database, with the given format: `<movie title> (<genre>, <screen time in minutes> minutes)` |



##### Room

| Variable | Type | Name | Description |
| -- | -- | -- | -- |
| ID | `long` | `movieId` | The identifier of the room. **Identifies the room in the database.** |
| Name | `String` | `roomName` | The name of the room. **Identifies the room in the application.** |
| Rows | `int` | `seatRows` | Number of seat rows the room has. |
| Columns | `int` | `seatColumns` | Number of seat columns the room has. |

###### Commands

| Name | Syntax | Privileged command | Description |
| -- | -- | -- | -- |
| Create room | `create room <room name> <number of seat rows> <number of seat columns>` | [x] | If a room not exists with the given name, creates it with the given parameters. |
| List rooms | `list rooms` | [] | Lists all rooms from the database. |
| Update room | `update room <room name> <number of seat rows> <number of seat columns>` | [x] | If a room exists with the given name, it's number of seat rows and columns are changes with the given parameters. |
| Delete room | `delete room <room name>` | [x] | If a room exists with the given name, deletes it. |

####### Outputs

| Command | Requirement(s) | Requirement satisfied | Output |
| -- | -- | -- | -- |
| List rooms | There is at least one room in the database | [] | `There are no rooms at the moment` |
| List rooms | There is at least one room in the database | [x] | All the screenings from the database, with the given format: `Room <room name> with <number of seats> seats, <number of seat rows> rows and <number of seat columns> columns ` |



##### Screening

| Variable | Type | Name | Description |
| -- | -- | -- | -- |
| ID | `UUID` | `movieId` | The identifier of the screening. **Identifies the screening in the database.** |
| Movie title | `String` | `movieTitle` | The title of the movie being screened. **Identifies the screening in the application.** |
| Room name | `String` | `roomName` | The name of the room the screening takes place. **Identifies the screening in the application.** |
| Date | `Date` | `screeningTime` | The date and time of the screening. **Identifies the screening in the application.** |

###### Commands

| Name | Syntax | Privileged command | Description |
| -- | -- | -- | -- |
| Create screening | `create screening <movie title> <room name> <screening time, in YYYY-MM-DD hh:mm format>` | [x] | If a screening not exists with the given room name, and the screening time is not *overlapping* creates it with the given parameters. |
| List screenings | `list screenings` | [] | Lists all screenings from the database. |
| Delete screening | `delete screening <movie title> <room name> <screening time, in YYYY-MM-DD hh:mm format>` | [x] | If a screening exists with the given parameters, deletes it. |

####### Outputs

| Command | Requirement(s) | Requirement satisfied | Output |
| -- | -- | -- | -- |
| Create screening | There is no screening in the given room when the new screening is starting | [] | `There is an overlapping screening` |
| Create screening | There was no screening in the given room 10 minutes prior to the new screening is starting | [] | `This would start in the break period after another screening in this room` |
| List screenings | There is at least one screening in the database  | [] | `There are no screenings` |
| List screenings | There is at least one screening in the database  | [x] | All the screenings from the database, with the given format: `<movie title> (<genre>, <screening time in minutes> minutes), screened in room <room name>, at <screening time, in YYYY-MM-DD hh:mm format>` |



##### User

| Variable | Type | Name | Description |
| -- | -- | -- | -- |
| ID | `UUID` | `userId` | The identifier of the user. **Identifies the user in the database.** |
| Username | `String` | `userName` | The username of the user. **Identifies the user in the application.** |
| Password | `String` | `userPassword` | The password of the user **Authenticates the user in the application.** |
| Administrator role | `boolean` | `isPrivileged` | It stores the privileges of the user. **Grants privileges to the user in the application.** |
|  |  |  |  |

###### Commands

| Name | Syntax | Privileged command | Description |
| -- | -- | -- | -- |
| Sign in | `sign in privileged <username> <password>` | [x] | If a user with the given username exists, it's a privileged account, and the password is correct the user is signed in. |
| Sign out | `sign out` | [x] | Signs out the user. |
| Describe account | `describe account` | [x] | If a privileged user is signed in, it tells the username, and that the account is privileged or not. |

####### Outputs

| Command | Requirement(s) | Requirement satisfied | Output |
| -- | -- | -- | -- |
| Sign in | Correct credentials | [] | `Login failed due to incorrect credentials` |
| Describe account | User is signed in | [] | `You are not signed in` |
| Describe account | User is signed in | [x] | `Signed in with privileged account '<username>'` |

---
