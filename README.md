# PetFinder.My

DSCI 551 Project, React+SpringBoot+MySQL

Special function: Big File chunked Upload

the SpringBoot Server codes are inside demo directory.

forgive me for the poor naming...

## Problem clarification

As the leading animal welfare platform in Malaysia, PetFinder.my has been maintaining a
database of over 15000 animals. Recently, It is experimenting with AI tools to help homeless
pets find people who are willing to adopt them. This project is yet another endeavor in the wave.
As it is arguably true that animal adoption rates are strongly correlated to their online profiles,
this project aims to solve an analytics problem: Make a prediction of how quickly, if at all, a pet is
adopted, given a fair amount of profile data consisting of both text data and non-text data.

## Data Collection

The team would use a kaggle data set:
https://www.kaggle.com/c/petfinder-adoption-prediction/data

## Functions

1. Data loading, exploring and cleaning
   The web application would have an intuitive interface for users to upload datasets. Upon
   uploading data, users should be able to explore the data. For example, the number of rows and
   attributes should be displayed. The application would also allow data cleaning in case the
   dataset has not-so-good data quality.
2. Feature extraction
   Users would extract desired features from datasets as input to ML algorithms
3. ML algorithms selection and model design
   Users would be able to choose one of ML models or algorithms to apply on their selected
   feature. Also, users would be able to design the structure of the model (such as dimension of
   input and output; neuron network depth and width).
4. Model training and model evaluation
   After the selection of feature, the App will be able to split train set and test set automatically, and
   then use train set to train model, finally use test set to evaluate model (metrics such as precision
   and recall will be recorded)
5. Feature set and Models managing
   The application would support users to manage different versions of feature sets and models
6. Data cloud storage and distributed computation
   Our project is designed to deal with a large volume of data and during model training
   computations can be very complex, so all above data are expected to be stored in AWS cloud
   platform, and time-consuming data processes will be finished distributedly.
7. Data visualization
   All above functions will be visualized to the website front-end. For example, when users explore
   data, they can see the plot of the distribution of the data on the websites, when users construct
   a model, he/she can see the visualized model structure.
