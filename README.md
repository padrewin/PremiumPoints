![](https://imgur.com/mekl311.png)<br>
[![Total Downloads](https://img.shields.io/github/downloads/padrewin/PremiumPoints/total?logo=coin&color=635aa7)](https://github.com/padrewin/PremiumPoints/releases)
&nbsp;&nbsp;
[![Latest Release](https://img.shields.io/github/v/release/padrewin/PremiumPoints?style=flat&logo=github&color=635aa7)](https://github.com/padrewin/PremiumPoints/packages/2256282)

#
* PremiumPoints is a currency Minecraft plugin, adding the ability to manager points for every player, with SQLite and MySQL database support.<br>
* This plugin is supporting **SQLite** and **MySQL** database. Default will be SQLite but can be edited in `config.yml` file.<br>
* This is a plugin based on a structure plugin named `ColdDev`, a continuation of PlayerPoints plugin by `black_ixx` which can be found [**here**](https://github.com/Mitsugaru/PlayerPoints).

## 📖 Documentation
All information is included and can be found in [**this Wiki's**](https://github.com/padrewin/PremiumPoints/wiki) repository.<br>
For any confusion you can join our [**Discord server**](https://discord.colddev.dev). Here you can get all the support you need.<br>

## ⚙️ Server compatibility<br>
PremiumPoints is compatible with Spigot and any forks of it.<br>
Recommending using Paper.<br>
CraftBukkit is **NOT** and **will NOT** be supported.<br>
![](https://imgur.com/Bt82udk.png)<br>

## </> For developers
<p>
    <a href="https://github.com/padrewin/PremiumPoints/releases">
        <img alt="spigot" src="https://img.shields.io/github/v/release/padrewin/PremiumPoints?style=for-the-badge&logo=github&color=00SS00"/>
    </a>
</p>

PremiumPoints is a standalone plugin, so you will need to install it on any servers that have plugins which depend on it, and specify it as a dependency in your plugin.yml:
```plugin.yml
depend:
  - PremiumPoints
```
```plugin.yml
softdepend:
  - PremiumPoints
```
Maven:
- repository
```pom.xml
<repository>
   <id>com.github.padrewin</id>
   <url>https://maven.pkg.github.com/padrewin/PremiumPoints</url>
</repository>
```
- dependency
- Replace `TAG` with the latest version available; example `1.3.3`.
```pom.xml
<dependency>
  <groupId>dev.padrewin</groupId>
  <artifactId>premiumpoints</artifactId>
  <version>TAG</version>
  <scope>provided</scope>
</dependency>
```
