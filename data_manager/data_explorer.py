import pandas as pd
from sklearn.impute import SimpleImputer


def data_explore(path):
    df=pd.read_csv(path)
    print('data has',df.shape[0],'rows and ',df.shape[1],'columns')
    print('data columns are:', list(df.columns))


def data_clean(data):
    imp = SimpleImputer(missing_values='Nan', strategy='mean')
    data_imputed_np = imp.fit_transform(data)
    print('replaced missing data with mean')
    return data_imputed_np


if __name__=='__main__':
    data_explore('/Users/chenyuqin/Desktop/21_fall_codes_and_relative/dsci551/project/data/train/train.csv')