## Remote Fetch App

The solution proposed uses the following Android tech stack/libraries: 

- Coil for Async Image fetching
- Once for checking if the first image has been fetched (Also Datastore or SharedPreferences could have been used)
- Retrofit for the Api Service
- Room for storing the local database
- Compose for UI
- Hilt for dependency injection
- A personal re-adaptation of MVI design pattern, to make it simpler and clean with Compose
- Coroutines/Flows for asynchronous operations. 

There are a few comments in the code that better explain some design choices.
For the sack of simplicity and to keep it shorter in terms of time, the UI is quite simple, and no personalized launcher icons/app icons have been submitted. 
