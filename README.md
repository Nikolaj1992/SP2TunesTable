## Music TunesTable API

This Readme is a copy of the early documentation for our SP2 project. As a result, parts of this is out of date.
It does however show our initial plans and gives an picture of what has since changed.

## Project Description
ToneTable/TunesTable(bare et udkast) - music game/quiz api

Vores ide er at lave en musik API, evt med brug af Spotify's web API for developers. Vores API bør kunne holde musikgenrer
, kunstnere og albummer med tilhørende numre. Intentionen med vores API er det ville kunne blive brugt af os selv eller
andre til at kreere en hjemmeside eller blot program, hvilket vil kunne bruge information om de kunstnere, album og sange
til at skabe en form for musik quiz spil eller lignende.

Documentation example music REST API:

Method	    URL	                            Request Body (JSON)	        Response (JSON)	            Error (e)	AccessLvl
GET		    /api/genres		                                            [genre, genre, ...](1)	    	        User
GET		    /api/genres/{id}        		                            genre (1)	                (e1)        User
POST	    /api/genres	                    genre(1) without id		                                (e2)        Admin
UPDATE	    /api/genres/{id}	            genre(1) without id	        genre (1)		                        Admin
POST	    /api/auth/register	            user(2)		                                            (e3)	    Anyone
POST	    /api/auth/login	                user(2)	                                        	    (e4)	    Anyone
GET	        /api/genres/artists		                                    [artist, artist, …](3)		            User
GET	        /api/genres/artists/{id}		                            artist(3)	                (e5)	    User
POST	    /api/genres/artists	            artist(3) without id		                            (e6)	    Admin
UPDATE	    /api/genres/artists/{id}	    artist(3) without id			                                    Admin
GET	        /api/genres/artists/{id}/albums		                        artist, [albums [  tracks] ](4)	    	User

Comment: Due to the data structure of the Spotify API, we have foregone genres in favour of albums, artists and songs.
         This has naturally changed our endpoints.

Request Body and Response Formats

(1) Genre format

 {
    "id": Number,
    "name": String [“Pop” | ”Retro”]
  }


(2) User format

 {
    "username": String,
    "password": String,
    “roles”: [
               {
                “name”: “User”
               }
            ]
  }


(3) Artist format

 {
    "id": Number
    "name": String,
    "popularity": Number
  }

(4) Album format

{
  "id": Number,
  "name": String,
  "popularity": Number,
  "albums": [
    {
      "id": Number,
      "title": String,
      "release_date": String, // "YYYY-MM-DD"
      "tracks": [
        {
          "id": Number,
          "name": String,
          "duration": Number // Track duration in seconds
        }
      ]
    }
  ]
}

Errors
(e) All errors are reported using this format (with the HTTP-status code matching the number)
{ status : statusCode, "msg": "Explains the problem" }

●	(e1) : { status : 404, "msg": "No music genre with id XX found" }
●	(e2) : { status : 400, "msg": "Missing a required field" }
●	(e3):{ status : 409, “msg”: "User with username XX already exists" }
●	(e4):{ status : 401, “msg”: "Login failed. Wrong password" }
●	(e5):{ status : 404, “msg”: "No artist with id XX found" }
●	(e6):{ status : 400, “msg”: "Missing required field" }


