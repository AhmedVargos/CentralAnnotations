<h1 align="center">
Central Annotations
<br>
</h1>

<h4 align="center">A simple annotations to help making your code centred around the user.</h4>

<p align="center">
<a href="https://jitpack.io/#ahmedvargos/centralannotations">
    <img src="https://jitpack.io/v/ahmedvargos/centralannotations.svg"
         alt="Badge">
  </a>
</p>

<p align="center">
  <a href="#introduction">Introduction</a> •
  <a href="#key-features">Key Features</a> •
  <a href="#how-to-use">How To Use</a> •
  <a href="#future-work">Future Work</a> •
  <a href="#license">License</a>
</p>

## Introduction
Annotation triggered method call for controlling the execution of methods using the concepts of AOP.

In most cases in our application, we have some different types of Users, plans and so on, each with different layouts and functionality unique to them, and we have to always handle this with a list of If conditions or Switch cases like these.
```
switch(user.type){
  case NORMAL:
        setAsNormalUser();
        break;
  case PREMIUM:
        setAsPremiumUser();
        break;
  case ADMIN:
        setAsAdmin();
}
```
Shouldn't it be easier?

By just adding `@RestrictToType(type = YOUR_DESIRED_TYPE)` and call it normally and it will not get executed unless it's this type.

## Key Features

* Control the execution of methods
* Log the entry of the methods you want

## How To Use

First add jitpack repository in your Project level `build.gradle`.
```
repositories {
        maven { url "https://jitpack.io" }
    }

```
Then add it to your project by this line.
```
 classpath 'com.github.ahmedvargos.centralannotations:central-main-plugin:1.0'
```
Finally apply the plugin in your module level `build.gradle`
```
apply plguin: 'central'
```

To use it first set the type of your user or plan by this line.
```
 Central.setType(NORMAL_USER); //it can be in your application class or any location to set the user type
```
Then call all the methods normally without cases, and just add the annotation with the type before it like this:
```
@RestrictToType(type = NORMAL_USER)
    private void setAsNormalUser() {
        textView.setText("Normal");
    }
 ```
Or adding more than just one type like this:
 ```
 @RestrictToType(type = {NORMAL_USER, ADMIN})
```
But make sure that the types are **Integers**.

You could also use the `@LogAtEntry` annotation to log in the debug if the method is entered or not.
 

## Future Work
• Adding support to kotlin <br>
• Adding more helpful annotations to the library

## License

MIT
