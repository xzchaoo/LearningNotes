package com.github.xzchaoo.bilibili.accounts.security;

import org.apache.commons.codec.binary.Base64;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignedObject;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;
import java.util.zip.CRC32;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Administrator on 2016/10/27.
 */
public class KeyPairGeneratorMain {
	/**
	 * rsa签名
	 *
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		KeyPair kp = kpg.generateKeyPair();

		Signature s = Signature.getInstance("MD5WithRSA");
		s.initSign(kp.getPrivate());
		byte[] data = "你好师姐".getBytes();
		s.update(data);
		byte[] result = s.sign();


		s.initVerify(kp.getPublic());
		s.update(data);
		boolean status = s.verify(result);
		System.out.println(status);
	}

	/**
	 * dh算法描述:1. 甲 生成 公钥和秘钥  将 公钥 发给乙
	 * 2. 乙根据甲的公钥 (以它作为一个参数) 生成自己的公钥和密钥, 将自己的公钥发给甲
	 * 3. 甲根据 自己的私钥和乙的公钥 可以生成出一个 对称加密的密钥
	 * 4. 乙根据 自己的私钥和甲的公钥 可以生成出一个 对称加密的密钥
	 * 5. 这两个密钥是一样的 (当然了 甲乙要选用相同的对称加密算法)
	 * 6. 以后的通信双方就采用这个对称加密算法!
	 */
	public static void DHdemo(String[] args) throws Exception {
		final String ALG = "DH";
		final String ALG2 = "AES";

		//1.
		KeyPairGenerator kpg1 = KeyPairGenerator.getInstance(ALG);
		//kpg1.initialize(1024);//DH算法要求size是 512~1024 的 64的倍数 默认是1024
		KeyPair kp1 = kpg1.generateKeyPair();
		DHPublicKey publicKey1 = (DHPublicKey) kp1.getPublic();
		DHPrivateKey privateKey1 = (DHPrivateKey) kp1.getPrivate();

		byte[] publicKeyBytes1 = publicKey1.getEncoded();

		//2.
		//2.1 接受甲的公钥

		//乙 获得的 甲的密钥
		DHPublicKey publicKey21 = (DHPublicKey) KeyFactory.getInstance(ALG).generatePublic(new X509EncodedKeySpec(publicKeyBytes1));

		KeyPairGenerator kpg2 = KeyPairGenerator.getInstance(ALG);
		kpg2.initialize(publicKey21.getParams());//注意这里!
		KeyPair kp2 = kpg2.generateKeyPair();
		DHPublicKey publicKey2 = (DHPublicKey) kp2.getPublic();
		DHPrivateKey privateKey2 = (DHPrivateKey) kp2.getPrivate();

		//3. 甲生成密钥
		DHPublicKey publicKey12 = (DHPublicKey) KeyFactory.getInstance(ALG).generatePublic(new X509EncodedKeySpec(publicKey2.getEncoded()));
		KeyAgreement ka1 = KeyAgreement.getInstance(ALG);
		ka1.init(privateKey1);
		ka1.doPhase(publicKey12, true);
		SecretKey sk1 = ka1.generateSecret(ALG2);

		//4. 乙生成密钥
		KeyAgreement ka2 = KeyAgreement.getInstance(ALG);
		ka2.init(privateKey2);
		ka2.doPhase(publicKey21, true);
		SecretKey sk2 = ka2.generateSecret(ALG2);

		//5. 从此以后双方采用 ALG2 算法进行交互 密钥是 sk1 和 sk2 (他们是相等的)
		System.out.println(Base64.encodeBase64String(sk1.getEncoded()).equals(Base64.encodeBase64String(sk2.getEncoded())));
	}

	public static void PBEdemo(String[] args) throws Exception {
		final String ALG = "PBEWithMD5AndDES";

		final int ITERATION_COUNT = 100;
		SecureRandom sr = new SecureRandom();
		byte[] salt = sr.generateSeed(8);

		String password = "70862045";
		PBEKeySpec ks = new PBEKeySpec(password.toCharArray());
		SecretKey sk = SecretKeyFactory.getInstance(ALG).generateSecret(ks);
		PBEParameterSpec ps = new PBEParameterSpec(salt, ITERATION_COUNT);
		Cipher c = Cipher.getInstance(ALG);
		c.init(Cipher.ENCRYPT_MODE, sk, ps);
		byte[] result = c.doFinal("你好师姐".getBytes());

		c.init(Cipher.DECRYPT_MODE, sk, ps);
		byte[] origin = c.doFinal(result);
		System.out.println(new String(origin));
	}

	public static void AESdemo2(String[] args) throws Exception {
		KeyGenerator kg = KeyGenerator.getInstance("AES");
		kg.init(256);
		SecretKey sk = kg.generateKey();
		System.out.println(sk.getEncoded().length);
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, sk);
		byte[] result = cipher.doFinal("你好师姐".getBytes());

		SecretKeySpec sks = new SecretKeySpec(sk.getEncoded(), kg.getAlgorithm());
		SecretKey sk2 = sks;
		cipher.init(Cipher.DECRYPT_MODE, sk2);
		String origin = new String(cipher.doFinal(result));
		System.out.println(origin);
	}

	public static void DESdemo2(String[] args) throws Exception {
		KeyGenerator kg = KeyGenerator.getInstance("DES");
		SecretKey sk = kg.generateKey();
		Cipher cipher = Cipher.getInstance(kg.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, sk);
		byte[] result = cipher.doFinal("你好师姐".getBytes());

		DESKeySpec sks = new DESKeySpec(sk.getEncoded());
		SecretKey sk2 = SecretKeyFactory.getInstance("DES").generateSecret(sks);
		cipher.init(Cipher.DECRYPT_MODE, sk2);
		String origin = new String(cipher.doFinal(result));
		System.out.println(origin);
	}

	public static void CRC32demo(String[] args) throws Exception {
		//CheckedInputStream
		//CheckedOutputStream
		CRC32 crc32 = new CRC32();
		crc32.update("你好师姐".getBytes());
		System.out.println(crc32.getValue());
	}

	//RSA 也可用于签名
	public static void DSAdemo(String[] args) throws Exception {
		byte[] data = "你好师姐".getBytes();
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA");
		kpg.initialize(1024);
		KeyPair kp = kpg.generateKeyPair();

		System.out.println(kpg.getAlgorithm());
		Signature signature = Signature.getInstance(kpg.getAlgorithm());
		signature.initSign(kp.getPrivate());
		signature.update(data);
		byte[] signResult = signature.sign();

		signature.initVerify(kp.getPublic());
		signature.update(data);
		boolean ok = signature.verify(signResult);
		System.out.println(ok);


		//对一个对象进行签名
		SignedObject so = new SignedObject("你好师姐", kp.getPrivate(), signature);
		so.getObject();//这个对象值相等 但其实已经不是原来的对象了

		//然后你将这个so对象远程发给别人 别人拿到之后先转成 so 假设转换成功
		//然后用下面的方法进行验证 看签名是否正确
		System.out.println(so.verify(kp.getPublic(), signature));
	}

	public static void AESdemo(String[] args) throws Exception {
		//生成密钥
		KeyGenerator kg = KeyGenerator.getInstance("AES");
		kg.init(256);
		SecretKey secretKey1 = kg.generateKey();
		//key->byte[]
		byte[] secretKeyBytes1 = secretKey1.getEncoded();
		System.out.println(secretKeyBytes1.length);

		//byte[] -> key
		SecretKeySpec keySpec = new SecretKeySpec(secretKeyBytes1, "AES");
		SecretKey secretKey2 = keySpec;

		System.out.println(Objects.deepEquals(secretKey1, secretKey2));

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey1);
		byte[] encrypted = cipher.doFinal("你好师姐".getBytes());

		cipher.init(Cipher.DECRYPT_MODE, secretKey1);
		String text = new String(cipher.doFinal(encrypted));
		System.out.println(text);
	}

	public static void DESdemo(String[] args) throws Exception {
		//生成密钥
		KeyGenerator kg = KeyGenerator.getInstance("DES");
		SecretKey secretKey1 = kg.generateKey();
		//key->byte[]
		byte[] secretKeyBytes1 = secretKey1.getEncoded();

		//byte[] -> key
		DESKeySpec desKeySpec = new DESKeySpec(secretKeyBytes1);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey2 = skf.generateSecret(desKeySpec);

		System.out.println(Objects.deepEquals(secretKey1, secretKey2));

		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey1);
		byte[] encrypted = cipher.doFinal("你好师姐".getBytes());

		cipher.init(Cipher.DECRYPT_MODE, secretKey1);
		String text = new String(cipher.doFinal(encrypted));
		System.out.println(text);
	}

	public static void RC2demo(String[] args) throws Exception {
		//生成密钥
		KeyGenerator kg = KeyGenerator.getInstance("RC2");
		SecretKey secretKey1 = kg.generateKey();

		//key -> byte[]
		byte[] secretKeyBytes1 = secretKey1.getEncoded();

		//byte[] -> key
		//因为 SecretKeySpec 本身就是一个 SecretKey 所以不需要 SecretKeyFactory 的帮助
		SecretKeySpec secretKeySpec1 = new SecretKeySpec(secretKeyBytes1, "RC2");
		SecretKey secretKey2 = secretKeySpec1;
		byte[] secretKeyBytes2 = secretKey2.getEncoded();
		System.out.println(Objects.deepEquals(secretKeyBytes1, secretKeyBytes2));

	}

	public static void RSAdemo(String[] args) throws Exception {
		//生成一个密钥对
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(1024);
		KeyPair kp = kpg.generateKeyPair();

		//XxxKey -> byte[]
		PublicKey publicKey1 = kp.getPublic();
		PrivateKey privateKey1 = kp.getPrivate();
		byte[] publicKeyBytes1 = publicKey1.getEncoded();
		byte[] privateKeyBytes1 = privateKey1.getEncoded();


		//byte[] -> XxxKey
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes1);//公钥只能用x509
		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes1);//私钥只能用pkcs8
		KeyFactory kf = KeyFactory.getInstance("RSA/ECB/PKCS1Padding");
		PublicKey publicKey2 = kf.generatePublic(publicKeySpec);
		PrivateKey privateKey2 = kf.generatePrivate(privateKeySpec);

		byte[] publicKeyBytes2 = publicKey2.getEncoded();
		byte[] privateKeyBytes2 = privateKey2.getEncoded();
		System.out.println(Objects.deepEquals(publicKeyBytes1, publicKeyBytes2));
		System.out.println(Objects.deepEquals(privateKeyBytes1, privateKeyBytes2));

		//根据x钥规范/材料生成x钥
		//KeyFactory kf = KeyFactory.getInstance("RSA");
		//kf.getKeySpec()
		//kf.translateKey()
	}

	public static void main4(String[] args) throws Exception {
		//keystore 称作密钥库 一般类型是jks 其实还有pkcs12 和 jceks 但是因为出口限制只有pkcs12 单pkcs12不是很好用 所以一般都是jks
		//一个ks 可以用于存放 密钥 或 证书
		KeyStore ks = KeyStore.getInstance("JKS");
		//ks.load();
		//ks.store();
		//ks.aliases()
		//ks.getCertificate()
		System.out.println("你好世界");
	}
}

