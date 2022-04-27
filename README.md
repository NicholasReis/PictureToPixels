## Description
Facial recognition software is evolving faster than our laws to regulate them; this forces those who value privacy and security into an "opt-out" position.
PictureToPixels was created to temporarily combat this.
This project allows you to take pictures you have and pixelate them to your preffered obfuscation. It can combine every two pixels into one or every sixty-four pixels into one.

## How It Works (Functional)
Upon opening you will be asked to select a picture you would like to use. Once selected there is a slider that will show you in real-time what your image will look like. You will have the option to save and/or select a different photo at any point.

## How It Works (Technical)
PictureToPixels is built on JavaFX. You will need to have the libraries installed in order to run this program. Once the user selects the photo they want to
use the program will render it into a fixed-sized imageview. The slider value will send how many surrounding pixels to group together and will average the
RGB colour values.

<!-- ## How It Works (Visual) (Will implement later) -->

## What's Next
Possible extensions to this project:
- Recreate using a web-based UI on my website
- Allow batches of images (perhaps selecting a folder instead of a file)
- Offer support for gifs
