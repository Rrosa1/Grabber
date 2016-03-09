# Scripting Data Schema

```javascript
{
  "$schema": "http://json-schema.org/draft-04/schema#",
  "title": "Scripting data model",
  "type": "object",
  "properties": {
    "ip": {
      "description": "Host IP",
      "type": "string"
    },
    "error": {
      "description": "Error message",
      "type": "string"
    },
    "response": {
      "description": "Response message of scripting packet",
      "type": "string"
    }
  },
  "required": [
    "ip"
  ]
}
```