# SpinnerPickerDialog
Date Spinner Picker Dialog

## Feature
* Show date picker with spinner style
* Fully Customizable style

## Contributing

You can do :
* a pull request, or
* raise a an issue ticket, or
* request additional feature by raise a ticket.


## License

What license? 

## Download
#### Gradle
**Step 1.** Add it in your root build.gradle at the end of repositories:
```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

**Step 2.** Add the dependency
```gradle
dependencies {
  implementation 'com.github.anggastudio:SpinnerPickerDialog:1.0'
}
```
#### Maven
**Step 1.**
```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>
```

**Step 2.** Add the dependency
```xml
<dependency>
  <groupId>com.github.anggastudio</groupId>
  <artifactId>SpinnerPickerDialog</artifactId>
  <version>1.0</version>
</dependency>
```

## Usage
* Load image with size as tall as line height (auto width)
```java
final SpinnerPickerDialog spinnerPickerDialog = new SpinnerPickerDialog();
spinnerPickerDialog.setContext(this);
spinnerPickerDialog.setOnDialogListener(new SpinnerPickerDialog.OnDialogListener() {

            @Override
            public void onSetDate(int month, int day, int year) {
                // "  (Month selected is 0 indexed {0 == January})"
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onDismiss() {
                
            }
            
        });
spinnerPickerDialog.show(this.getSupportFragmentManager(), "");        
```