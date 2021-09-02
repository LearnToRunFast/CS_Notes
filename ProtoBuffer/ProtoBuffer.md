```protobuf
// The syntax for this file is proto3
/* Person is used to identify users
 * across our system*/
syntax = "proto3";
import "Full_path_here"; 

package person; // define package

// number are tag
message Person {
	int32 age = 1;
	string first_name = 2;
	string last_name = 3;
	bytes small_picture = 4;
	bool is_verified = 5;
	float height = 6;
	repeated string phone_number = 7;
	
	enum EyeColor {
		UNKNOWN_COLOR = 0; // always start 0
		EYE_GREEN = 1;
		EYE_RED = 2;
	}
	EyeColor eye_color = 8;
	Date birthday = 9;
	
	message Address {
		string address_line_1 = 1;
		string address_line_2 = 2;
		string zip_code = 3;
		string city = 4;
		string country = 5;
	}
	repeated Address addresses = 10;
}
message Date {
	int32 year = 1;
	int32 month = 2;
	int32 day = 3;
}
```

### Tags

Tags numbered form 1 to 15 use 1byte in space, so use them for frequently populated fields

Tags numbered from 16 to 2047 use 2 bytes

Largest tag : $2^{29} - 1$

Can't use the numbers 19000 to 19999

## Update Rule

Always set default value tag as `0`

### Adding Fields

1. Old code will ignore new column
2. Old data with new code will get default value for that new column

### Renaming Fields

You can rename field as needed.

### Removing Fields

```protobuf
message MyMessage {
	reserved 2;
	reserverd "first_name";
	// you can resverd for same type
	// but you can't mix them
	reserved 9 to 11;
	reserved "foo", "bar";
	int32 id = 1;
}
```

