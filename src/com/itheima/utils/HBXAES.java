package com.itheima.utils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * 
 * <dl>
 * 	<dd>文件名称：HBXAES.java</dd>
 * 	<dd>文件描述：des加密</dd>
 * 	<dd>版权所有：红背心服务网</dd>
 * 	<dd>公&#12288;&#12288;司：汇安居(北京)信息科技有限公司</dd>
 * 	<dd>内容摘要：本文件的内容是….，包括主要….模块、…函数及 功能是………</dd>
 * 	<dd>其他说明：</dd>
 * 	<dd>创建时间：2017年8月3日 上午10:36:31</dd>
 * 	<dd>@author：何虎军</dd>
 * </dl>
 */
public class HBXAES {

	/**
	 * 
	 * <dl>
	 * 	<dd>方法描述:加密</dd>
	 * 	<dd>@param：</dd>
	 * 	<dd>@return：byte[]</dd>
	 * 	<dd>@author：何虎军&#12288;email:hehujun@126.com&#12288;phone:13716418863</dd>
	 * 	<dd>创建时间：2017年8月3日 上午10:36:24</dd>
	 * </dl>
	 */
	private static byte[] encrypt(byte[] content, byte[] keyBytes, byte[] iv) {
		try {
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(keyBytes);
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, random);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key2 = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key2, new IvParameterSpec(iv));
			byte[] result = cipher.doFinal(content);
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * <dl>
	 * 	<dd>方法描述:解密</dd>
	 * 	<dd>@param：</dd>
	 * 	<dd>@return：byte[]</dd>
	 * 	<dd>@author：何虎军&#12288;email:hehujun@126.com&#12288;phone:13716418863</dd>
	 * 	<dd>创建时间：2017年8月3日 上午10:36:40</dd>
	 * </dl>
	 */
	private static byte[] decrypt(byte[] content, byte[] keyBytes, byte[] iv) {
		try {
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(keyBytes);
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, random);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key2 = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key2, new IvParameterSpec(iv));
			byte[] result = cipher.doFinal(content);
			return result;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 
	 * <dl>
	 * 	<dd>方法描述:加密</dd>
	 * 	<dd>@param：</dd>
	 * 	<dd>@return：String</dd>
	 * 	<dd>@author：何虎军&#12288;email:hehujun@126.com&#12288;phone:13716418863</dd>
	 * 	<dd>创建时间：2017年8月3日 上午11:06:39</dd>
	 * </dl>
	 */
	public static String encrypt(String content) {
		try {
			byte[] result = encrypt(content.getBytes(), key.getBytes(), iv.getBytes());
			return Base64.encodeBase64String(result);
		} catch (Exception e) {
			return null;
		}
		
	}
	
	/**
	 * 
	 * <dl>
	 * 	<dd>方法描述:解密</dd>
	 * 	<dd>@param：</dd>
	 * 	<dd>@return：String</dd>
	 * 	<dd>@author：何虎军&#12288;email:hehujun@126.com&#12288;phone:13716418863</dd>
	 * 	<dd>创建时间：2017年8月3日 上午11:08:53</dd>
	 * </dl>
	 */
	public static String decrypt(String content) {
		try {
			byte[] result = decrypt(Base64.decodeBase64(content), key.getBytes(), iv.getBytes());
			return new String(result);
		} catch (Exception e) {
			return null;
		}		
	}
	
	private static String key = "abhj89yz";
	private static String iv = "abefhjlmopqrtwyz";
	private HBXAES() {}
	
	public static void main(String[] args) {
		String content = "hello一二三四五六七八九十hello一二三四五六七八九十hello一二三四五六七八九十hello一二三四五六七八九十hello一二三四五六七八九十hello一二三四五六七八九十hello一二三四五六七八九十";

		System.out.println("加密前：" + content);
		String encrypted = encrypt(content);
		System.out.println("加密后：" + encrypted);
		System.out.println("解密后：" + decrypt(encrypted));
		
	}
}