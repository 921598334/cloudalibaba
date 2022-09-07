# 工程简介
可以用于spring cloud项目快速创建，创建方法：
直接把现有的服务拷贝过来修改：
1.pom的artifactId
2.删除xxx.iml
3.父工程pom添加子moddule
4.更新maven
5.修改工程文件类型，如java文件夹改为source root
6.主类名称修改
7.maven->clean,install


如果提示找不到主类可以参考：
https://www.bigtspace.com/archives/9697.html

如果出现在程序中没有任何报错，但是编译时提示找不到符号或者类，这种情况是在应用包含公共类的maven工程中出现，此时先不引用，然后maven clean后再install，再尝试引用


# 延伸阅读

