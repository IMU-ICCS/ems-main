# CRUD Datasource

## GET /ds

**Description**

Returns all datasources in the database.

## GET /ds/{id}

**Description**

Returns one datasource matching the given id.

**Parameters**

| Parameter | Type  | Description      |
| ----------|:-----:| ---------------- |
| id        | long  |  Identifier of the datasource, mandatory|

## DELETE /ds/{id}

**Description**

Unmounts and deletes the datasource matching the given id.

**Parameters**

| Parameter | Type  | Description      |
| ----------|:-----:| ---------------- |
| id        | long  |  Identifier of the datasource, mandatory|

## POST (add) /ds

**Description**

Adds the datasource in the body to the database and mounts the mount point.

**JSON Body (Request)**
```json
{
    "name":"DSXYZ",
    "type":"HDFS",
    "mountPoint":"/melodic/DSXYZZZ",
    "ufsURI":"s3a://mesosbucket"
}
```

## PUT (update) /ds/{id}

**Description**

Updates the datasource with the given id with the data provided for the datasource in the body.

**Parameters**

| Parameter | Type  | Description      |
| ----------|:-----:| ---------------- |
| id        | long  |  Identifier of the datasource, mandatory|

**JSON Body (Request)**
```json
{
    "name":"DSXYZ",
    "type":"HDFS",
    "mountPoint":"/melodic/DSXYZZZ",
    "ufsURI":"s3a://mesosbucket"
}
```

# Migrate Datasource

## POST /migrate/file

**Description**

Migrates (moves) a file from pathFrom to pathTo in the body data.

**JSON Body (Request)**
```json
{
    "pathFrom":"/melodic/ds2/x.txt", 
    "pathTo":"/melodic/ds2/y.txt"
}
```

## POST /migrate/dir

**Description**

Migrates (moves) a directory from pathFrom to pathTo in the body data.

**JSON Body (Request)**
```json
{
    "pathFrom":"/melodic/ds2/dir1", 
    "pathTo":"/melodic/ds2/dir2"
}
```

## POST /migrate/ds

**Description**

Migrates (moves) the complete datasource (and all data) with the given id to the location in pathTo in the body data.

**JSON Body (Request)**
```json
{
    "id":"2", 
    "pathTo":"/melodic/ds2/dir2"
}
```