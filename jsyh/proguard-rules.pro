# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-optimizationpasses 7
#
# ----------------------------- 类和方法 -----------------------------
#
# 保持指定包下的类名，包括子包下的类名,方法名，字段名
-keep class com.jsyh.project**{
       public <fields>;

       public <methods>;
}

#-keep class com.jsyh.project.VBindingActivity {
#     public <fields>;
#}
#-keep class com.jsyh.project.VBindingActivity {
#    public <methods>;
#}

#
##不混淆某个包所有的类
#-keep class com.jsyh.project.** { *; }

#-keepclassmembers class com.jsyh.project.** {
#    <methods>;
#}
