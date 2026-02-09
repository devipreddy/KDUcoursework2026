import boto3
ddb = boto3.client("dynamodb")

def handler(event, context):
    res = ddb.get_item(
        TableName="Devi-Prasad-CounterTable",
        Key={"counterId": {"S": "main"}}
    )

    count = res.get("Item", {}).get("count", {"N": "0"})["N"]

    return {
        "statusCode": 200,
        "body": count
    }
