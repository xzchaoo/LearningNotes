用于发布组件到maven仓库的时候对你的文件进行签名



基本用法

http://maven.apache.org/plugins/maven-gpg-plugin/usage.html



建议在你的settings.xml里进行如下的配置:

    <profile>
      <id>gpg</id>
      <properties>
        <gpg.passphrase>你的密码</gpg.passphrase>
      </properties>
    </profile>


需要部署的时候使用 -Pgpg 使得配置生效



# 常用的配置

keyname 当你的gpg管理了多个用户的时候, 需要使用它来指定哪个用户进行签名, 用于gpg的 --local-user

passphrase 用于你的密钥的passphrase

skip 跳过gpg的签名

