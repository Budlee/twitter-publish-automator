# Twitter publish automator

A simple application to push up tweets

## Configuration

### OAuth config
Set the environment variables from your Twitter app (check [here](https://auth0.com/docs/connections/social/twitter) on how to create them)
```shell script
TWEET_ACCESS_TOKEN=my_access_token
TWEET_ACCESS_SECRET=my_access_secret
TWEET_CONSUMER_KEY=my_consumer_key
TWEET_CONSUMER_SECRET=my_consumer_secret
```

### Status publishing
A simple two value CSV file which has two values has a default location of `src/main/resources/myTwitter.csv`

You can override this file to set your CSV file by configuring `tweet.store.file`

The two values in the CSV are Status and Epoch (UTC) Time


An example of the CSV
```csv
Check out the original project at https://github.com/Budlee/twitter-publish-automator,1569172494
```

## TODO 
   * Create build pipeline
   * Push docker image with a "How To"
