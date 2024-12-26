
# Aldi Assessment

This is an assessment for the Mobile App Developer position at Aldi Süd. The repository is public for now but will make it private should the Aldi team request me to do so.

A brief summary of the app:
- Load a list of the top 10 ranked coins from the [CoinCap REST API](https://docs.coincap.io/)
- Upon clicking on an list item, display more details about the Coin
- The data are to be refresh automatically every 60 seconds
- The app must follow the screen designs provided.

## Notes
- The coins are already sorted in ascending order according to [https://docs.coincap.io](https://docs.coincap.io/) and there was an option to send a parameter to the API to limit the results to 10. No sorting or truncation was needed from the app side.

- I implemented a snackbar that shows up if trying to load the list if the API call is not successful.

- I added a swipe to refresh mechanism in the list view.

- The SVGs from figma weren't working, as if the extracted svg was malformed. Since i refused to import them as pngs, I downloaded the biggest PNG size, imported them in Illustrator and used its image trace to generate an SVG. I admit it's not an elegant solution. In a real scenario, I would have asked the designer for help.

- If the icon is not present in figma, the "Blur On" icon from [Material Symbols & Icons](https://fonts.google.com/icons) is used as default.

---

## Instructions to run

