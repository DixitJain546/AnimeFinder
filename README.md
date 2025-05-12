Anime Finder App
An Android application that fetches and displays a list of anime series using the Jikan API. Users can view detailed information and trailers for each anime.

Features
Anime List Page: Displays a list of top-rated anime with their title, number of episodes, rating, and poster image.

Anime Detail Page: Shows detailed information about the selected anime, including:

Trailer video (if available) or poster image.

Title and synopsis.

Genres and Number of episodes and rating.

API Endpoints Used
Top Anime: https://api.jikan.moe/v4/top/anime

Technologies Used
Programming Language: Kotlin

Architecture: MVVM (Model-View-ViewModel)

Networking: Retrofit for API calls

Image Loading: Glide

Video Playback: Android YouTube Player Library

Assumptions Made
The Jikan API provides reliable and consistent data for anime series.

Trailer URLs provided by the API are valid YouTube links.

The app targets devices running Android 13.0 (API level 33) and above.

Known Limitations
The app does not handle pagination for the anime list. It fetches only the first page of top anime.

Error handling for network failures is basic and could be enhanced for better user experience.

The app does not support searching for specific anime titles.

The YouTube Player may not function correctly if the YouTube app is not installed on the device.
