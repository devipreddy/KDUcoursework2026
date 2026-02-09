import boto3
ddb = boto3.client("dynamodb")

def handler(event, context):
    res = ddb.update_item(
        TableName="Devi-Prasad-CounterTable",
        Key={"counterId": {"S": "main"}},
        UpdateExpression="ADD #c :inc",
        ExpressionAttributeNames={"#c": "count"},
        ExpressionAttributeValues={":inc": {"N": "1"}},
        ReturnValues="UPDATED_NEW"
    )

    return {
        "statusCode": 200,
        "body": res["Attributes"]["count"]["N"]
    }
