package meteo.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


/**
 * Calculate and compare checksums
 * @author fimar
 *
 */
public class Checksums
{
	private String cacheDir;
	private MessageDigest md;

	public Checksums(String cacheDir)
	{
		this.cacheDir = cacheDir;
		
		try
		{
			md = MessageDigest.getInstance("MD5");
		} catch( NoSuchAlgorithmException e1 ) { throw new RuntimeException(e1); }
	}
	
	/**
	 * Checks whether the filename has changed;
	 * The checksum will be cached using tag value, if updateCache flag is true
	 * @param filename
	 * @param tag
	 * @param updateCache
	 * @return
	 * @throws IOException
	 */
	public boolean check(String filename, String tag, boolean updateCache) throws IOException
	{
		String oldChecksum = readChecksum(filename, tag);
		String newChecksum = calcChecksum(filename);
		
		if( !newChecksum.equals(oldChecksum) )
		{
			if( updateCache )
			{
				writeChecksum(filename, tag, newChecksum);
			}
			return false;
		}
		
		return true;
	}
	
	private void writeChecksum( String filename, String tag, String checksum ) throws IOException
	{
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(getChecksumCacheName(filename, tag))))
		{
			writer.write(checksum);
		}
	}

	private String readChecksum(String filename, String tag) throws IOException
	{
		try(BufferedReader reader = new BufferedReader(new FileReader(getChecksumCacheName(filename, tag))))
		{
			return reader.readLine();
		}
		catch(FileNotFoundException e)
		{
			return null; 
		}
	}
	
	private String getChecksumCacheName( String filename, String tag )
	{
			return new StringBuilder(cacheDir).append("/")
					
					.append(new File(filename).getName()).append("_").append(tag)
					.toString();
		
	}

	private String calcChecksum(String filename) throws IOException
	{
		
		try (InputStream is = Files.newInputStream(Paths.get(filename));
		     DigestInputStream dis = new DigestInputStream(is, md)) 
		{
		  /* Read decorated stream (dis) to EOF as normal... */
		}
		
		byte[] digest = md.digest();
		return new String(Base64.getEncoder().encode(digest));
	}

	public static void main(String ...strings ) throws IOException
	{
		Checksums checksums = new Checksums("cache");
		
		boolean result = checksums.check("src/test/resources/checksum.test", "test", true);
		System.out.println(result);
	}
}
