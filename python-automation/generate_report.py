import boto3
import pandas as pd
from datetime import datetime
import os

dynamodb = boto3.resource('dynamodb', region_name='us-east-2')
s3 = boto3.client('s3', region_name='us-east-2')

TABLE_NAME = "nimbus-audit"
BUCKET_NAME = "nimbus-audit-reports"

def fetch_data():
    table = dynamodb.Table(TABLE_NAME)
    response = table.scan()
    return response['Items']

def generate_csv(data):
    df = pd.DataFrame(data)

    filename = os.path.join(
        os.getcwd(),
        f"audit_report_{datetime.now().strftime('%Y%m%d%H%M%S')}.csv"
    )
    df.to_csv(filename, index=False)

    return filename

def upload_to_s3(file_path):
    file_name = file_path.split("/")[-1]

    s3.upload_file(file_path, BUCKET_NAME, file_name)

    print(f"Uploaded to S3: {file_name}")

def main():
    print("Fetching data from DynamoDB...")
    data = fetch_data()

    print("Generating CSV...")
    file_path = generate_csv(data)

    print("Uploading to S3...")
    upload_to_s3(file_path)

    print("Done!")

if __name__ == "__main__":
    main()