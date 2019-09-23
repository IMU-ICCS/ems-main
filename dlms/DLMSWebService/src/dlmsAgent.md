# Event API

## POST (add) /event

**Description**

Registers a new event and makes it ready to be called.
The event name must be unique among the other registered events.

**JSON Body (Request)**
   ```json
   {
      "name":"testevent",
      "command":"ls -all"
   }
   ```
## DELETE /event?eventName={eventName}

**Description**

Unregisters the event with the given name.

Returns an error if no event with this name could be found. Note that the search for event names is NOT case sensitive.

**Parameters**

| Parameter |  Type  | Description      |
| ----------|:------:| ---------------- |
| eventName | String |  Name of the event, mandatory|

## DELETE /event/all

**Description**

Removes all events from the database.

## GET /event/all

**Description**

Returns all events in the database.

## GET /event?eventName={eventName}

**Description**

Returns the current status of the event with the given name.

Returns an error if no event with this name could be found. Note that the search for event names is NOT case sensitive.

**Parameters**

| Parameter |  Type  | Description      |
| ----------|:------:| ---------------- |
| eventName | String |  Name of the event, mandatory|

## GET /event/status?eventName={eventName}

**Description**

Returns the current status of the event with the given name.

Returns an error if no event with this name could be found. Note that the search for event names is NOT case sensitive.

**Parameters**

| Parameter |  Type  | Description      |
| ----------|:------:| ---------------- |
| eventName | String |  Name of the event, mandatory|

## GET /event/send?eventName={eventName}

**Description**

Triggers the event with the given name and executes the event's command.
Returns a String with the command's resulting output or an empty String if there was no output.

Success can be determined by querying the event's status afterwards. A failed execution will have status ERROR, a successful run will have status FINISHED.
Note that "successful" in this case means that the command could be called without the operating system complaining about anything and not that the command completely did what it was intended to do.

Returns an error if no event with this name could be found. Note that the search for event names is NOT case sensitive.
Returns an error if the event's current status is RUNNING, as an event can only be running once at a time.

**Parameters**

| Parameter |  Type  | Description      |
| ----------|:------:| ---------------- |
| eventName | String |  Name of the event, mandatory|
