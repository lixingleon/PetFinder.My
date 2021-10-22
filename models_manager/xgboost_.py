import pandas as pd 
import xgboost as xgb
from sklearn import metrics
import os

# print(1)
# df=pd.read_csv('/Users/chenyuqin/Desktop/21_fall_codes_and_relative/dsci551/project/data/train/train.csv')
# print(3)
# train, test=df[:14000], df[14000:]
# train_y, test_y=train['AdoptionSpeed'], test['AdoptionSpeed']
# train_x=train[['Age', 'Breed1', 'Breed2', 'Gender', 'Color1', 'Color2',
#        'Color3', 'MaturitySize', 'FurLength', 'Vaccinated', 'Dewormed',
#        'Sterilized', 'Health', 'Quantity', 'Fee', 'State']]
# test_x=test[['Age', 'Breed1', 'Breed2', 'Gender', 'Color1', 'Color2',
#        'Color3', 'MaturitySize', 'FurLength', 'Vaccinated', 'Dewormed',
#        'Sterilized', 'Health', 'Quantity', 'Fee', 'State']]
# dtrain=xgb.DMatrix(train_x, label=train_y)
# dtest=xgb.DMatrix(test_x, label=test_y)
# model = xgb.train(params={}, dtrain=dtrain)
# pred_y = model.predict(dtest)
# accuracy = metrics.mean_squared_error(test_y,pred_y)
# print(2)
# print(accuracy)

def train_and_get_metrics(path):
    df=pd.read_csv(path)
    train, test=df[:14000], df[14000:]
    train_y, test_y=train['AdoptionSpeed'], test['AdoptionSpeed']
    train_x=train[['Age', 'Breed1', 'Breed2', 'Gender', 'Color1', 'Color2',
       'Color3', 'MaturitySize', 'FurLength', 'Vaccinated', 'Dewormed',
       'Sterilized', 'Health', 'Quantity', 'Fee', 'State']]
    test_x=test[['Age', 'Breed1', 'Breed2', 'Gender', 'Color1', 'Color2',
       'Color3', 'MaturitySize', 'FurLength', 'Vaccinated', 'Dewormed',
       'Sterilized', 'Health', 'Quantity', 'Fee', 'State']]
    dtrain=xgb.DMatrix(train_x, label=train_y)
    dtest=xgb.DMatrix(test_x, label=test_y)
    model = xgb.train(params={}, dtrain=dtrain)
    pred_y = model.predict(dtest)
    accuracy = metrics.mean_squared_error(test_y,pred_y)
    return accuracy

if __name__=='__main__':
   # print(os.path.abspath('..'))
#    print(train_and_get_metrics('../data/train/train.csv'))
   print(train_and_get_metrics('/Users/chenyuqin/Desktop/21_fall_codes_and_relative/dsci551/project/data/train/train.csv'))