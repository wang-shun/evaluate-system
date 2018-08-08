package mycat.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

/**
 * SVN工具
 * <功能详细描述>
 * 
 * @author  xWX483171
 * @version  [版本号, 2018年8月6日]
 * @since  [云服务/模块版本]
 */
public class SvnLogUtil
{
    
    public static void toGetSvnLog(String url, String name, String password, long startRevision, long endRevision)
    {
        SVNRepository repository = null;
        Collection<?> logEntries = null;
        try
        {
            SVNRepositoryFactoryImpl.setup();
            repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
            ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
            repository.setAuthenticationManager(authManager);
            
            logEntries = repository.log(new String[] {""}, null, startRevision, endRevision, true, true);
        }
        catch (Exception e)
        {
            System.out.println("Get svn log error, "+e.getMessage());
            e.printStackTrace();
            return;
        }
        for (Iterator<?> entries = logEntries.iterator(); entries.hasNext();)
        {
            SVNLogEntry logEntry = (SVNLogEntry)entries.next();
            System.out.println("---------------------------------------------");
            System.out.println("revision: " + logEntry.getRevision());
            System.out.println("author: " + logEntry.getAuthor());
            System.out.println("date: " + logEntry.getDate());
            System.out.println("log message: " + logEntry.getMessage());
            
            if (logEntry.getChangedPaths().size() > 0)
            {
                System.out.println();
                System.out.println("changed paths:");
                Set<?> changedPathsSet = logEntry.getChangedPaths().keySet();
                
                for (Iterator<?> changedPaths = changedPathsSet.iterator(); changedPaths.hasNext();)
                {
                    SVNLogEntryPath entryPath = (SVNLogEntryPath)logEntry.getChangedPaths().get(changedPaths.next());
                    System.out.println(" " + entryPath.getType() + " " + entryPath.getPath()
                        + ((entryPath.getCopyPath() != null)
                            ? " (from " + entryPath.getCopyPath() + " revision " + entryPath.getCopyRevision() + ")"
                            : ""));
                }
            }
        }
    }
    
    /*public static void main(String[] args)
    {
        // TODO Auto-generated method stub
        *//**
         * root root123456
         *//*
        SvnLogUtil.toGetSvnLog("svn://49.4.65.137/persionpro", "admin", "admin123456", 0, -1);
    }*/
    
}
