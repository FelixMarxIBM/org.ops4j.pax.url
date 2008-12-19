package org.ops4j.pax.url.dir.internal.bundle;

import java.io.File;
import java.io.IOException;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import static junit.framework.Assert.*;
import org.junit.Test;
import org.ops4j.pax.url.dir.internal.NullOutputStream;
import org.ops4j.pax.url.dir.internal.bundle.ResourceLocatorImpl;

/**
 * @author Toni Menzel (tonit)
 * @since Dec 11, 2008
 */
public class ResourceLocatorImplTest
{

    @Test( expected = IllegalArgumentException.class )
    public void simpleFailingAtConstruct()
        throws IOException
    {
        ResourceLocatorImpl loc = new ResourceLocatorImpl( null, "" );
    }

    @Test( expected = IllegalArgumentException.class )
    public void simpleFailingAtWriteToNull()
        throws IOException
    {
        ResourceLocatorImpl loc = new ResourceLocatorImpl( new File( "." ), "" );
        loc.write( null );
    }

    @Test
    public void testRoot()
        throws IOException
    {
        String clazz = this.getClass().getName().replaceAll( "\\.", "/" ) + ".class";
        ResourceLocatorImpl loc = new ResourceLocatorImpl( new File( "." ), clazz );
        final int[] countOfEntries = new int[]{ 0 };

        JarOutputStream out = new JarOutputStream( new NullOutputStream() )
        {

            @Override
            public void putNextEntry( ZipEntry zipEntry )
                throws IOException
            {
                super.putNextEntry( zipEntry );
                countOfEntries[ 0 ]++;
            }


        };
        assertEquals( "test-classes", loc.getRoot().getName() );
        loc.write( out );

        assertEquals( 8, countOfEntries[ 0 ] );
    }
}