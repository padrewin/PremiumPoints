[![PremiumPoints](https://imgur.com/mekl311.png)](https://github.com/padrewin/PremiumPoints)<br>

[![Total Downloads](https://img.shields.io/github/downloads/padrewin/PremiumPoints/total?logo=coin&color=635aa7)](https://github.com/padrewin/PremiumPoints/releases)
&nbsp;
[![Latest Release](https://img.shields.io/github/v/release/padrewin/PremiumPoints?style=flat&logo=github&color=635aa7)](https://github.com/padrewin/PremiumPoints/packages/2256282)
&nbsp;
[![Jenkins Build](https://img.shields.io/jenkins/build?jobUrl=https%3A%2F%2Fjenkins.colddev.dev%2Fjob%2FCold%2520Development%2Fjob%2FPremiumPoints%2F&style=flat&logo=jenkins&logoColor=white&logoSize=auto&label=Jenkins)](https://jenkins.colddev.dev/job/Cold%20Development/job/PremiumPoints/)
&nbsp;
[![GitHub Actions Workflow Status](https://img.shields.io/github/actions/workflow/status/padrewin/PremiumPoints/release.yml?branch=master&style=flat&logo=github&label=GitHub)](https://github.com/padrewin/PremiumPoints/actions/workflows/release.yml)

* **PremiumPoints** is a currency Minecraft plugin, adding the ability to manager points for every player, with SQLite and MySQL database support.<br>
* Supports **`SQLite`** and **`MySQL`** database. Default database is SQLite which can be edited later in `config.yml` file.<br>
* This plugin is based on [**`ColdDev`**](https://github.com/Cold-Development/ColdDev) plugin library.
* This is a continuation of [**`PlayerPoints`**](https://github.com/Mitsugaru/PlayerPoints) plugin, originally developed by `black_ixx`.

---
## 📖 Documentation
All information is included and can be found in this repository's [**Wiki**](https://github.com/padrewin/PremiumPoints/wiki).<br>
For any questions or support, you can join our [**Discord server**](https://discord.colddev.dev). Here you can find all the support you need.<br>
![](https://imgur.com/Bt82udk.png)

---
## ⚙️ Server compatibility<br>
PremiumPoints is compatible with Spigot and any forks of it.<br>
> [!NOTE]
> Recommending using Paper.<br>
> CraftBukkit is **NOT** and **will NOT** be supported.

---
## </> For developers
<p>
    <a href="https://github.com/padrewin/PremiumPoints/releases">
        <img alt="spigot" src="https://img.shields.io/github/v/release/padrewin/PremiumPoints?style=for-the-badge&logo=github&color=00SS00"/>
    </a>
</p>

PremiumPoints is a standalone plugin, so you will need to install it on any servers that have plugins which depend on it, and specify it as a dependency in your plugin.yml:<br>
```plugin.yml
depend:
  - PremiumPoints
```

### Maven:
- Repository<br>

```pom.xml
<repository>
   <id>com.github.padrewin</id>
   <url>https://maven.pkg.github.com/padrewin/PremiumPoints</url>
</repository>
```
- Dependency
  - Replace `TAG` with the latest version available; example `1.3.3`.<br>
  
```pom.xml
<dependency>
  <groupId>dev.padrewin</groupId>
  <artifactId>premiumpoints</artifactId>
  <version>TAG</version>
  <scope>provided</scope>
</dependency>
```
![](https://raw.githubusercontent.com/mayhemantt/mayhemantt/Update/svg/Bottom.svg)
