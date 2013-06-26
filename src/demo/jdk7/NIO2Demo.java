package demo.jdk7;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.file.DirectoryStream;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.AclEntryType;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.FileStoreAttributeView;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

public class NIO2Demo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private void pathTest() {

		try {
			// FileSystems.getDefault().getPath(first, more)
			Path path = Paths.get(System.getProperty("user.home"), "www",
					"pyweb.settings");
			Path real_path = path.toRealPath(LinkOption.NOFOLLOW_LINKS);
			System.out.println("Path to real path: " + real_path);

			System.out.println("Number of name elements in path: "
					+ path.getNameCount());
			for (int i = 0; i < path.getNameCount(); i++) {
				System.out.println("Name element " + i + " is: "
						+ path.getName(i));
			}
			System.out.println("Subpath (0,3): " + path.subpath(0, 3));

			File path_to_file = path.toFile();
			Path file_to_path = path_to_file.toPath();
			System.out.println("Path to file name: " + path_to_file.getName());
			System.out.println("File to path: " + file_to_path.toString());

			Path base = Paths.get(System.getProperty("user.home"), "www",
					"pyweb.settings");
			// resolve AEGON.txt file
			Path path1 = base.resolve("django.wsgi");
			System.out.println(path1.toString());

			Path path2 = base.resolveSibling(".bashrc");
			System.out.println(path2.toString());

			Path path01_to_path02 = path1.relativize(path2);
			System.out.println(path01_to_path02);

			try {
				boolean check = Files.isSameFile(path1.getParent(),
						path2.getParent());
				if (check) {
					System.out.println("The paths locate the same file!"); // true
				} else {
					System.out
							.println("The paths does not locate the same file!");
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}

			boolean sw = path1.startsWith("/rafaelnadal/tournaments");
			boolean ew = path1.endsWith("django.wsgi");
			System.out.println(sw);
			System.out.println(ew);

			for (Path name : path1) {
				System.out.println(name);
			}

		} catch (NoSuchFileException e) {
			System.err.println(e);
		} catch (IOException e) {
			System.err.println(e);
		}

	}

	private void fileAttributeTest() {

		FileSystem fs = FileSystems.getDefault();
		Set<String> views = fs.supportedFileAttributeViews();
		for (String view : views) {
			System.out.println(view);
		}
		/*
		 * BasicFileAttributeView: This is a view of basic attributes that must
		 * be supported by all file system implementations. The attribute view
		 * name is basic. • DosFileAttributeView: This view provides the
		 * standard four supported attributes on file systems that support the
		 * DOS attributes. The attribute view name is dos. •
		 * PosixFileAttributeView: This view extends the basic attribute view
		 * with attributes supported on file systems that support the POSIX
		 * (Portable Operating System Interface for Unix) family of standards,
		 * such as Unix. The attribute view name is posix. •
		 * FileOwnerAttributeView: This view is supported by any file system
		 * implementation that supports the concept of a file owner. The
		 * attribute view name is owner. • AclFileAttributeView: This view
		 * supports reading or updating a file’s ACL. The NFSv4 ACL model is
		 * supported. The attribute view name is acl.
		 * UserDefinedFileAttributeView: This view enables support of metadata
		 * that is user defined.
		 */

		for (FileStore store : fs.getFileStores()) {
			boolean supported = store
					.supportsFileAttributeView(BasicFileAttributeView.class);
			System.out.println(store.name() + " ---" + supported);
		}
		Path path = null;
		try {
			path = Paths.get(System.getProperty("user.home"), "www",
					"pyweb.settings");
			FileStore store = Files.getFileStore(path);
			boolean supported = store.supportsFileAttributeView("basic");
			System.out.println(store.name() + " ---" + supported);
		} catch (IOException e) {
			System.err.println(e);
		}

		BasicFileAttributes attr = null;
		try {
			attr = Files.readAttributes(path, BasicFileAttributes.class);
		} catch (IOException e) {
			System.err.println(e);
		}
		System.out.println("File size: " + attr.size());
		System.out.println("File creation time: " + attr.creationTime());
		System.out.println("File was last accessed at: "
				+ attr.lastAccessTime());
		System.out.println("File was last modified at: "
				+ attr.lastModifiedTime());
		System.out.println("Is directory? " + attr.isDirectory());
		System.out.println("Is  regular file? " + attr.isRegularFile());
		System.out.println("Is  symbolic link? " + attr.isSymbolicLink());
		System.out.println("Is  other? " + attr.isOther());

		// 只获取某个属性 [view-name:]attribute-name
		/**
		 * Basic attribute names are listed here: lastModifiedTime
		 * lastAccessTime creationTime size isRegularFile isDirectory
		 * isSymbolicLink isOther fileKey
		 **/
		try {
			long size = (Long) Files.getAttribute(path, "basic:size",
					java.nio.file.LinkOption.NOFOLLOW_LINKS);
			System.out.println("Size: " + size);
		} catch (IOException e) {
			System.err.println(e);
		}
		// Update a Basic Attribute
		long time = System.currentTimeMillis();
		FileTime fileTime = FileTime.fromMillis(time);
		try {
			Files.getFileAttributeView(path, BasicFileAttributeView.class)
					.setTimes(fileTime, fileTime, fileTime);
		} catch (IOException e) {
			System.err.println(e);
		}
		try {
			Files.setLastModifiedTime(path, fileTime);
		} catch (IOException e) {
			System.err.println(e);
		}

		try {
			Files.setAttribute(path, "basic:lastModifiedTime", fileTime,
					NOFOLLOW_LINKS);
			Files.setAttribute(path, "basic:creationTime", fileTime,
					NOFOLLOW_LINKS);
			Files.setAttribute(path, "basic:lastAccessTime", fileTime,
					NOFOLLOW_LINKS);
		} catch (IOException e) {
			System.err.println(e);
		}
		// DosFileAttributeView DOS attributes can be acquired with the
		// following names:hidden readonly system archive
		DosFileAttributes docattr = null;
		try {
			docattr = Files.readAttributes(path, DosFileAttributes.class);
		} catch (IOException e) {
			System.err.println(e);
		}
		System.out.println("Is read only ? " + docattr.isReadOnly());
		System.out.println("Is Hidden ? " + docattr.isHidden());
		System.out.println("Is archive ? " + docattr.isArchive());
		System.out.println("Is  system ? " + docattr.isSystem());

		// FileOwnerAttributeView
		// Set a File Owner Using Files.setOwner() 三种设置文件所有者的方法
		UserPrincipal owner = null;
		try {
			owner = path.getFileSystem().getUserPrincipalLookupService()
					.lookupPrincipalByName("apress");
			Files.setOwner(path, owner);
		} catch (IOException e) {
			System.err.println(e);
		}
		FileOwnerAttributeView foav = Files.getFileAttributeView(path,
				FileOwnerAttributeView.class);
		try {
			owner = path.getFileSystem().getUserPrincipalLookupService()
					.lookupPrincipalByName("apress");
			foav.setOwner(owner);
		} catch (IOException e) {
			System.err.println(e);
		}
		try {
			owner = path.getFileSystem().getUserPrincipalLookupService()
					.lookupPrincipalByName("apress");
			Files.setAttribute(path, "owner:owner", owner, NOFOLLOW_LINKS);
		} catch (IOException e) {
			System.err.println(e);
		}
		// 获取文件所有者
		try {
			String ownerName = foav.getOwner().getName();
			System.out.println(ownerName);
		} catch (IOException e) {
			System.err.println(e);
		}
		try {
			UserPrincipal owner1 = (UserPrincipal) Files.getAttribute(path,
					"owner:owner", NOFOLLOW_LINKS);
			System.out.println(owner1.getName());
		} catch (IOException e) {
			System.err.println(e);
		}

		// POSIX View file owner, group owner, and nine related access
		// permissions (read, write, members of the same group, etc.). •group
		// permissions

		PosixFileAttributes positattr = null;
		try {
			positattr = Files.readAttributes(path, PosixFileAttributes.class);
		} catch (IOException e) {
			System.err.println(e);
		}
		System.out.println("File owner: " + positattr.owner().getName());
		System.out.println("File group: " + positattr.group().getName());
		System.out.println("File permissions: "
				+ positattr.permissions().toString());

		// 设置文件访问权限
		FileAttribute<Set<PosixFilePermission>> posixattrs = PosixFilePermissions
				.asFileAttribute(positattr.permissions());
		try {
			Files.createFile(path, posixattrs);
		} catch (IOException e) {
			System.err.println(e);
		}
		Set<PosixFilePermission> permissions = PosixFilePermissions
				.fromString("rw-r--r--");
		try {
			Files.setPosixFilePermissions(path, permissions);
		} catch (IOException e) {
			System.err.println(e);
		}

		// 设置分组用户
		try {
			GroupPrincipal group = path.getFileSystem()
					.getUserPrincipalLookupService()
					.lookupPrincipalByGroupName("apressteam");
			Files.getFileAttributeView(path, PosixFileAttributeView.class)
					.setGroup(group);
		} catch (IOException e) {
			System.err.println(e);
		}

		// 查询组用户
		try {
			GroupPrincipal group = (GroupPrincipal) Files.getAttribute(path,
					"posix:group", NOFOLLOW_LINKS);
			System.out.println(group.getName());
		} catch (IOException e) {
			System.err.println(e);
		}

		// ACL View access control list acl owner
		// 查询acl属性
		List<AclEntry> acllist = null;
		AclFileAttributeView aclview = Files.getFileAttributeView(path,
				AclFileAttributeView.class);
		try {
			acllist = aclview.getAcl();
			for (AclEntry aclentry : acllist) {
				System.out
						.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
				System.out.println("Principal: "
						+ aclentry.principal().getName());
				System.out.println("Type: " + aclentry.type().toString());
				System.out.println("Permissions: "
						+ aclentry.permissions().toString());
				System.out.println("Flags: " + aclentry.flags().toString());
			}
		} catch (Exception e) {
			System.err.println(e);
		}

		// 设置ACL属性
		try {
			// Lookup for the principal
			UserPrincipal user = path.getFileSystem()
					.getUserPrincipalLookupService()
					.lookupPrincipalByName("apress");
			// Get the ACL view
			AclFileAttributeView view = Files.getFileAttributeView(path,
					AclFileAttributeView.class);
			// Create a new entry
			AclEntry entry = AclEntry
					.newBuilder()
					.setType(AclEntryType.ALLOW)
					.setPrincipal(user)
					.setPermissions(AclEntryPermission.READ_DATA,
							AclEntryPermission.APPEND_DATA).build();
			// read ACL
			List<AclEntry> acl = view.getAcl();
			// Insert the new entry
			acl.add(0, entry);
			// rewrite ACL
			view.setAcl(acl);
			// or, like this
			// Files.setAttribute(path, "acl:acl", acl, NOFOLLOW_LINKS);
		} catch (IOException e) {
			System.err.println(e);
		}

		// File Store Attributes
		// 获取所有的fifilestore的属性信息
		FileSystem fs1 = FileSystems.getDefault();
		for (FileStore store : fs1.getFileStores()) {
			try {
				long total_space = store.getTotalSpace() / 1024;
				long used_space = (store.getTotalSpace() - store
						.getUnallocatedSpace()) / 1024;
				long available_space = store.getUsableSpace() / 1024;
				boolean is_read_only = store.isReadOnly();
				System.out.println("--- " + store.name() + " --- "
						+ store.type());
				System.out.println("Total space: " + total_space);
				System.out.println("Used space: " + used_space);
				System.out.println("Available space: " + available_space);
				System.out.println("Is read only? " + is_read_only);
			} catch (IOException e) {
				System.err.println(e);
			}
		}

		// 获取某个文件的fifilestore，再查询filestroe的属性信息
		try {
			FileStore store = Files.getFileStore(path);
			FileStoreAttributeView fsav = store
					.getFileStoreAttributeView(FileStoreAttributeView.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// User-Defined File Attributes View 用户自定义文件属性
		// 检测文件系统是否支持自定义属性
		try {
			FileStore store = Files.getFileStore(path);
			if (!store
					.supportsFileAttributeView(UserDefinedFileAttributeView.class)) {
				System.out
						.println("The user defined attributes are not supported on: "
								+ store);
			} else {
				System.out
						.println("The user defined attributes are supported on: "
								+ store);
			}
		} catch (IOException e) {
			System.err.println(e);
		}
		// 设置文件属性
		UserDefinedFileAttributeView udfav = Files.getFileAttributeView(path,
				UserDefinedFileAttributeView.class);
		try {
			int written = udfav.write(
					"file.description",
					Charset.defaultCharset().encode(
							"This file contains private information!"));
			System.out.println("write user defined file attribute return :"
					+ written);
		} catch (IOException e) {
			System.err.println(e);
		}
		// 获取文件的所有自定义属性
		try {
			for (String name : udfav.list()) {
				System.out.println(udfav.size(name) + "" + name);
			}
		} catch (IOException e) {
			System.err.println(e);
		}
		try {
			int size = udfav.size("file.description");
			ByteBuffer bb = ByteBuffer.allocateDirect(size);
			udfav.read("file.description", bb);
			bb.flip();
			System.out.println(Charset.defaultCharset().decode(bb).toString());
		} catch (IOException e) {
			System.err.println(e);
		}
		// 删除自定义文件属性
		try {
			udfav.delete("file.description");
		} catch (IOException e) {
			System.err.println(e);
		}

	}

	private void fileLinkTest() {

		Path link = FileSystems.getDefault().getPath(
				System.getProperty("user.home"), "www", "pyweb.settings");
		Path target = FileSystems.getDefault().getPath("testlink");

		// 创建软链接
		try {
			Files.createSymbolicLink(link, target);

			// 创建软链接时设置软链接的属性
			PosixFileAttributes attrs = Files.readAttributes(target,
					PosixFileAttributes.class);
			FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions
					.asFileAttribute(attrs.permissions());
			Files.createSymbolicLink(link, target, attr);

		} catch (IOException | UnsupportedOperationException
				| SecurityException e) {
			if (e instanceof SecurityException) {
				System.err.println("Permission denied!");
			}
			if (e instanceof UnsupportedOperationException) {
				System.err.println("An unsupported operation was detected!");
			}
			if (e instanceof IOException) {
				System.err.println("An I/O error occurred!");
			}
			System.err.println(e);
		}

		// 检查是否是软链接
		boolean link_isSymbolicLink_1 = Files.isSymbolicLink(link);
		boolean target_isSymbolicLink_1 = Files.isSymbolicLink(target);
		System.out.println(link.toString() + " is a symbolic link ? "
				+ link_isSymbolicLink_1);
		System.out.println(target.toString() + " is a symbolic link ? "
				+ target_isSymbolicLink_1);

		try {
			boolean link_isSymbolicLink_2 = (boolean) Files.getAttribute(link,
					"basic:isSymbolicLink");
			boolean target_isSymbolicLink_2 = (boolean) Files.getAttribute(
					target, "basic:isSymbolicLink");
			System.out.println(link.toString() + " is a symbolic link ? "
					+ link_isSymbolicLink_2);
			System.out.println(target.toString() + " is a symbolic link ? "
					+ target_isSymbolicLink_2);
		} catch (IOException | UnsupportedOperationException e) {
			System.err.println(e);
		}

		// 读取软链接对应的文件
		try {
			Path linkedpath = Files.readSymbolicLink(link);
			System.out.println(linkedpath.toString());
		} catch (IOException e) {
			System.err.println(e);
		}

		// 创建硬链接
		try {
			Files.createLink(link, target);
			System.out.println("The link was successfully created!");
		} catch (IOException | UnsupportedOperationException
				| SecurityException e) {
			if (e instanceof SecurityException) {
				System.err.println("Permission denied!");
			}
			if (e instanceof UnsupportedOperationException) {
				System.err.println("An unsupported operation was detected!");
			}
			if (e instanceof IOException) {
				System.err.println("An I/O error occured!");
			}
			System.err.println(e);
		}
	}

	private void fileOpe1Test() {

		Path path = FileSystems.getDefault().getPath(
				System.getProperty("user.home"), "www", "pyweb.settings");
		// 检查文件是否存在 exist, not exist, or unknown.
		// !Files.exists(...) is not equivalent to Files.notExists(...) and the
		// notExists() method is not a complement of the exists() method
		// 如果应用没有权限访问这个文件，则两者都返回false
		boolean path_exists = Files.exists(path,
				new LinkOption[] { LinkOption.NOFOLLOW_LINKS });
		boolean path_notexists = Files.notExists(path,
				new LinkOption[] { LinkOption.NOFOLLOW_LINKS });
		System.out.println(path_exists);
		System.out.println(path_notexists);

		// 检测文件访问权限
		boolean is_readable = Files.isReadable(path);
		boolean is_writable = Files.isWritable(path);
		boolean is_executable = Files.isExecutable(path);
		boolean is_regular = Files.isRegularFile(path,
				LinkOption.NOFOLLOW_LINKS);
		if ((is_readable) && (is_writable) && (is_executable) && (is_regular)) {
			System.out.println("The checked file is accessible!");
		} else {
			System.out.println("The checked file is not accessible!");
		}

		// 检测文件是否指定同一个文件
		Path path_1 = FileSystems.getDefault().getPath(
				System.getProperty("user.home"), "www", "pyweb.settings");
		Path path_2 = FileSystems.getDefault().getPath(
				System.getProperty("user.home"), "www", "django.wsgi");
		Path path_3 = FileSystems.getDefault().getPath(
				System.getProperty("user.home"), "software/../www",
				"pyweb.settings");
		try {
			boolean is_same_file_12 = Files.isSameFile(path_1, path_2);
			boolean is_same_file_13 = Files.isSameFile(path_1, path_3);
			boolean is_same_file_23 = Files.isSameFile(path_2, path_3);
			System.out.println("is same file 1&2 ? " + is_same_file_12);
			System.out.println("is same file 1&3 ? " + is_same_file_13);
			System.out.println("is same file 2&3 ? " + is_same_file_23);
		} catch (IOException e) {
			System.err.println(e);
		}

		// 检测文件可见行
		try {
			boolean is_hidden = Files.isHidden(path);
			System.out.println("Is hidden ? " + is_hidden);
		} catch (IOException e) {
			System.err.println(e);
		}

		// 获取文件系统根目录
		Iterable<Path> dirs = FileSystems.getDefault().getRootDirectories();
		for (Path name : dirs) {
			System.out.println(name);
		}
		// jdk6的API
		// File[] roots = File.listRoots();
		// for (File root : roots) {
		// System.out.println(root);
		// }

		// 创建新目录
		Path newdir = FileSystems.getDefault().getPath("/tmp/aaa");
		// try {
		// Files.createDirectory(newdir);
		// } catch (IOException e) {
		// System.err.println(e);
		// }
		Set<PosixFilePermission> perms = PosixFilePermissions
				.fromString("rwxr-x---");
		FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions
				.asFileAttribute(perms);
		try {
			Files.createDirectory(newdir, attr);
		} catch (IOException e) {
			System.err.println(e);
		}

		// 创建多级目录,创建bbb目录，在bbb目录下再创建ccc目录等等
		Path newdir2 = FileSystems.getDefault().getPath("/tmp/aaa",
				"/bbb/ccc/ddd");
		try {
			Files.createDirectories(newdir2);
		} catch (IOException e) {
			System.err.println(e);
		}

		// 列举目录信息
		Path newdir3 = FileSystems.getDefault().getPath("/tmp");
		try (DirectoryStream<Path> ds = Files.newDirectoryStream(newdir3)) {
			for (Path file : ds) {
				System.out.println(file.getFileName());
			}
		} catch (IOException e) {
			System.err.println(e);
		}
		// 通过正则表达式过滤
		System.out.println("\nGlob pattern applied:");
		try (DirectoryStream<Path> ds = Files.newDirectoryStream(newdir3,
				"*.{png,jpg,bmp，ini}")) {
			for (Path file : ds) {
				System.out.println(file.getFileName());
			}
		} catch (IOException e) {
			System.err.println(e);
		}

		// 创建新文件
		Path newfile = FileSystems.getDefault().getPath(
				"/tmp/SonyEricssonOpen.txt");
		Set<PosixFilePermission> perms1 = PosixFilePermissions
				.fromString("rw-------");
		FileAttribute<Set<PosixFilePermission>> attr2 = PosixFilePermissions
				.asFileAttribute(perms1);
		try {
			Files.createFile(newfile, attr2);
		} catch (IOException e) {
			System.err.println(e);
		}

		// 写小文件
		try {
			byte[] rf_wiki_byte = "test".getBytes("UTF-8");
			Files.write(newfile, rf_wiki_byte);
		} catch (IOException e) {
			System.err.println(e);
		}

		// 读小文件
		try {
			byte[] ballArray = Files.readAllBytes(newfile);
			System.out.println(ballArray.toString());
		} catch (IOException e) {
			System.out.println(e);
		}
		Charset charset = Charset.forName("ISO-8859-1");
		try {
			List<String> lines = Files.readAllLines(newfile, charset);
			for (String line : lines) {
				System.out.println(line);
			}
		} catch (IOException e) {
			System.out.println(e);
		}

		// 读写文件缓存流操作
		String text = "\nVamos Rafa!";
		try (BufferedWriter writer = Files.newBufferedWriter(newfile, charset,
				StandardOpenOption.APPEND)) {
			writer.write(text);
		} catch (IOException e) {
			System.err.println(e);
		}
		try (BufferedReader reader = Files.newBufferedReader(newfile, charset)) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			System.err.println(e);
		}

		// 不用缓存的输入输出流
		String racquet = "Racquet: Babolat AeroPro Drive GT";
		byte data[] = racquet.getBytes();
		try (OutputStream outputStream = Files.newOutputStream(newfile)) {
			outputStream.write(data);
		} catch (IOException e) {
			System.err.println(e);
		}
		String string = "\nString: Babolat RPM Blast 16";
		try (OutputStream outputStream = Files.newOutputStream(newfile,
				StandardOpenOption.APPEND);
				BufferedWriter writer = new BufferedWriter(
						new OutputStreamWriter(outputStream))) {
			writer.write(string);
		} catch (IOException e) {
			System.err.println(e);
		}
		int n;
		try (InputStream in = Files.newInputStream(newfile)) {
			while ((n = in.read()) != -1) {
				System.out.print((char) n);
			}
		} catch (IOException e) {
			System.err.println(e);
		}

		// 临时目录操作
		String tmp_dir_prefix = "nio_";
		try {
			// passing null prefix
			Path tmp_1 = Files.createTempDirectory(null);
			System.out.println("TMP: " + tmp_1.toString());
			// set a prefix
			Path tmp_2 = Files.createTempDirectory(tmp_dir_prefix);
			System.out.println("TMP: " + tmp_2.toString());

			// 删除临时目录
			Path basedir = FileSystems.getDefault().getPath("/tmp/aaa");
			Path tmp_dir = Files.createTempDirectory(basedir, tmp_dir_prefix);
			File asFile = tmp_dir.toFile();
			asFile.deleteOnExit();

		} catch (IOException e) {
			System.err.println(e);
		}

		String tmp_file_prefix = "rafa_";
		String tmp_file_sufix = ".txt";
		try {
			// passing null prefix/suffix
			Path tmp_1 = Files.createTempFile(null, null);
			System.out.println("TMP: " + tmp_1.toString());
			// set a prefix and a suffix
			Path tmp_2 = Files.createTempFile(tmp_file_prefix, tmp_file_sufix);
			System.out.println("TMP: " + tmp_2.toString());
			File asFile = tmp_2.toFile();
			asFile.deleteOnExit();

		} catch (IOException e) {
			System.err.println(e);
		}

		// 删除文件
		try {
			boolean success = Files.deleteIfExists(newdir2);
			System.out.println("Delete status: " + success);
		} catch (IOException | SecurityException e) {
			System.err.println(e);
		}

		// 拷贝文件
		Path copy_from = Paths.get("/tmp", "draw_template.txt");
		Path copy_to = Paths
				.get("/tmp/bbb", copy_from.getFileName().toString());
		try {
			Files.copy(copy_from, copy_to);
		} catch (IOException e) {
			System.err.println(e);
		}
		try (InputStream is = new FileInputStream(copy_from.toFile())) {
			Files.copy(is, copy_to);
		} catch (IOException e) {
			System.err.println(e);
		}
		// 移动文件
		Path movefrom = FileSystems.getDefault().getPath(
				"C:/rafaelnadal/rafa_2.jpg");
		Path moveto = FileSystems.getDefault().getPath(
				"C:/rafaelnadal/photos/rafa_2.jpg");
		try {
			Files.move(movefrom, moveto, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			System.err.println(e);
		}

	}

	private void workFilePath() {
		Path listDir = Paths.get("/tmp"); // define the starting file
		//
		ListTree walk = new ListTree();
		try {
			Files.walkFileTree(listDir, walk);
		} catch (IOException e) {
			System.err.println(e);
		}

		// 遍历的时候跟踪链接
		EnumSet opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
		try {
			Files.walkFileTree(listDir, opts, Integer.MAX_VALUE, walk);
		} catch (IOException e) {
			System.err.println(e);
		}

		// FileVisitor 提供perform a file search, a recursive copy, arecursive
		// move, and a recursive delete.

	}

	private void randAccessFile() {

		Path path = Paths.get("/tmp", "story.txt");

		// create the custom permissions attribute for the email.txt file
		Set<PosixFilePermission> perms = PosixFilePermissions
				.fromString("rw-r------");
		FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions
				.asFileAttribute(perms);

		// write a file using SeekableByteChannel
		try (SeekableByteChannel seekableByteChannel = Files.newByteChannel(
				path, EnumSet.of(StandardOpenOption.WRITE,
						StandardOpenOption.TRUNCATE_EXISTING), attr)) {
			ByteBuffer buffer = ByteBuffer
					.wrap("Rafa Nadal produced another masterclass of clay-court tennis to win his fifth French Open title ..."
							.getBytes());
			int write = seekableByteChannel.write(buffer);
			System.out.println("Number of written bytes: " + write);
			buffer.clear();
		} catch (IOException ex) {
			System.err.println(ex);
		}

		// read a file using SeekableByteChannel
		try (SeekableByteChannel seekableByteChannel = Files.newByteChannel(
				path, EnumSet.of(StandardOpenOption.READ), attr)) {
			ByteBuffer buffer = ByteBuffer.allocate(12);
			String encoding = System.getProperty("file.encoding");
			buffer.clear();
			// 随机访问定位API
			// seekableByteChannel.position();
			// seekableByteChannel.truncate(100);
			while (seekableByteChannel.read(buffer) > 0) {
				buffer.flip();
				System.out.print(Charset.forName(encoding).decode(buffer));
				buffer.clear();
			}

			// seekableByteChannel.position(seekableByteChannel.size()/2);

		} catch (IOException ex) {
			System.err.println(ex);
		}

		MappedByteBuffer buffer = null;
		try (FileChannel fileChannel = (FileChannel.open(path,
				EnumSet.of(StandardOpenOption.READ)))) {
			// 文件锁
			FileLock lock = fileChannel.lock();
			buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0,
					fileChannel.size());
			// 文件channel操作，从一个channel通道把数据传输到另外一个channel
			// fileChannel_from.transferTo(0L, fileChannel_from.size(),
			// fileChannel_to);
			lock.release();
		} catch (IOException ex) {
			System.err.println(ex);
		}
		if (buffer != null) {
			try {
				Charset charset = Charset.defaultCharset();
				CharsetDecoder decoder = charset.newDecoder();
				CharBuffer charBuffer = decoder.decode(buffer);
				String content = charBuffer.toString();
				System.out.println(content);
				buffer.clear();
			} catch (CharacterCodingException ex) {
				System.err.println(ex);
			}
		}
	}

	// WatchService 是线程安全的，跟踪文件事件的服务，一般是用独立线程启动跟踪
	public void watchRNDir(Path path) throws IOException, InterruptedException {
		try (WatchService watchService = FileSystems.getDefault()
				.newWatchService()) {
			// 给path路径加上文件观察服务
			path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
					StandardWatchEventKinds.ENTRY_MODIFY,
					StandardWatchEventKinds.ENTRY_DELETE);
			// start an infinite loop
			while (true) {
				// retrieve and remove the next watch key
				final WatchKey key = watchService.take();
				// get list of pending events for the watch key
				for (WatchEvent<?> watchEvent : key.pollEvents()) {
					// get the kind of event (create, modify, delete)
					final Kind<?> kind = watchEvent.kind();
					// handle OVERFLOW event
					if (kind == StandardWatchEventKinds.OVERFLOW) {
						continue;
					}
					// 创建事件
					if (kind == StandardWatchEventKinds.ENTRY_CREATE) {

					}
					// 修改事件
					if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {

					}
					// 删除事件
					if (kind == StandardWatchEventKinds.ENTRY_DELETE) {

					}
					// get the filename for the event
					final WatchEvent<Path> watchEventPath = (WatchEvent<Path>) watchEvent;
					final Path filename = watchEventPath.context();
					// print it out
					System.out.println(kind + " -> " + filename);

				}
				// reset the keyf
				boolean valid = key.reset();
				// exit loop if the key is not valid (if the directory was
				// deleted, for
				if (!valid) {
					break;
				}
			}
		}
	}
	
	// 异步文件读写示例  
    public static void asyFile() {  
        ByteBuffer buffer = ByteBuffer.allocate(100);  
        String encoding = System.getProperty("file.encoding");  
        Path path = Paths.get("/tmp", "store.txt");  
        try (AsynchronousFileChannel asynchronousFileChannel = AsynchronousFileChannel  
                .open(path, StandardOpenOption.READ)) {  
            Future<Integer> result = asynchronousFileChannel.read(buffer, 0);  
            // 读超时控制  
            // int count = result.get(100, TimeUnit.NANOSECONDS);  
  
            while (!result.isDone()) {  
                System.out.println("Do something else while reading ...");  
            }  
            System.out.println("Read done: " + result.isDone());  
            System.out.println("Bytes read: " + result.get());  
  
            // 使用CompletionHandler回调接口异步读取文件  
            final Thread current = Thread.currentThread();  
            asynchronousFileChannel.read(buffer, 0,  
                    "Read operation status ...",  
                    new CompletionHandler<Integer, Object>() {  
                        @Override  
                        public void completed(Integer result, Object attachment) {  
                            System.out.println(attachment);  
                            System.out.print("Read bytes: " + result);  
                            current.interrupt();  
                        }  
  
                        @Override  
                        public void failed(Throwable exc, Object attachment) {  
                            System.out.println(attachment);  
                            System.out.println("Error:" + exc);  
                            current.interrupt();  
                        }  
                    });  
  
        } catch (Exception ex) {  
            System.err.println(ex);  
        }  
        buffer.flip();  
        System.out.print(Charset.forName(encoding).decode(buffer));  
        buffer.clear();  
  
        // 异步文件写示例  
        ByteBuffer buffer1 = ByteBuffer  
                .wrap("The win keeps Nadal at the top of the heap in men's"  
                        .getBytes());  
        Path path1 = Paths.get("/tmp", "store.txt");  
        try (AsynchronousFileChannel asynchronousFileChannel = AsynchronousFileChannel  
                .open(path1, StandardOpenOption.WRITE)) {  
            Future<Integer> result = asynchronousFileChannel  
                    .write(buffer1, 100);  
            while (!result.isDone()) {  
                System.out.println("Do something else while writing ...");  
            }  
            System.out.println("Written done: " + result.isDone());  
            System.out.println("Bytes written: " + result.get());  
  
            // file lock  
            Future<FileLock> featureLock = asynchronousFileChannel.lock();  
            System.out.println("Waiting for the file to be locked ...");  
            FileLock lock = featureLock.get();  
            if (lock.isValid()) {  
                Future<Integer> featureWrite = asynchronousFileChannel.write(  
                        buffer, 0);  
                System.out.println("Waiting for the bytes to be written ...");  
                int written = featureWrite.get();  
                // or, use shortcut  
                // int written = asynchronousFileChannel.write(buffer,0).get();  
                System.out.println("I’ve written " + written + " bytes into "  
                        + path.getFileName() + " locked file!");  
                lock.release();  
            }  
  
            // asynchronousFileChannel.lock("Lock operation status:", new  
            // CompletionHandler<FileLock, Object>() ;  
  
        } catch (Exception ex) {  
            System.err.println(ex);  
        }  
    }  
  
    // public static AsynchronousFileChannel open(Path file, Set<? extends  
    // OpenOption> options,ExecutorService executor, FileAttribute<?>... attrs)  
    // throws IOException  
    private static Set withOptions() {  
        final Set options = new TreeSet<>();  
        options.add(StandardOpenOption.READ);  
        return options;  
    }  
  
    // 使用AsynchronousFileChannel.open(path, withOptions(),  
    // taskExecutor))这个API对异步文件IO的处理  
    public static void asyFileChannel2() {  
        final int THREADS = 5;  
        ExecutorService taskExecutor = Executors.newFixedThreadPool(THREADS);  
        String encoding = System.getProperty("file.encoding");  
        List<Future<ByteBuffer>> list = new ArrayList<>();  
        int sheeps = 0;  
        Path path = Paths.get("/tmp",  
                "store.txt");  
        try (AsynchronousFileChannel asynchronousFileChannel = AsynchronousFileChannel  
                .open(path, withOptions(), taskExecutor)) {  
            for (int i = 0; i < 50; i++) {  
                Callable<ByteBuffer> worker = new Callable<ByteBuffer>() {  
                    @Override  
                    public ByteBuffer call() throws Exception {  
                        ByteBuffer buffer = ByteBuffer  
                                .allocateDirect(ThreadLocalRandom.current()  
                                        .nextInt(100, 200));  
                        asynchronousFileChannel.read(buffer, ThreadLocalRandom  
                                .current().nextInt(0, 100));  
                        return buffer;  
                    }  
                };  
                Future<ByteBuffer> future = taskExecutor.submit(worker);  
                list.add(future);  
            }  
            // this will make the executor accept no new threads  
            // and finish all existing threads in the queue  
            taskExecutor.shutdown();  
            // wait until all threads are finished  
            while (!taskExecutor.isTerminated()) {  
                // do something else while the buffers are prepared  
                System.out  
                        .println("Counting sheep while filling up some buffers!So far I counted: "  
                                + (sheeps += 1));  
            }  
            System.out.println("\nDone! Here are the buffers:\n");  
            for (Future<ByteBuffer> future : list) {  
                ByteBuffer buffer = future.get();  
                System.out.println("\n\n" + buffer);  
                System.out  
                        .println("______________________________________________________");  
                buffer.flip();  
                System.out.print(Charset.forName(encoding).decode(buffer));  
                buffer.clear();  
            }  
        } catch (Exception ex) {  
            System.err.println(ex);  
        }  
    }  
  
    //异步server socket channel io处理示例  
    public static void asyServerSocketChannel() {  
          
        //使用threadGroup  
//      AsynchronousChannelGroup threadGroup = null;  
//      ExecutorService executorService = Executors  
//      .newCachedThreadPool(Executors.defaultThreadFactory());  
//      try {  
//      threadGroup = AsynchronousChannelGroup.withCachedThreadPool(executorService, 1);  
//      } catch (IOException ex) {  
//      System.err.println(ex);  
//      }  
//      AsynchronousServerSocketChannel asynchronousServerSocketChannel =  
//              AsynchronousServerSocketChannel.open(threadGroup);  
          
        final int DEFAULT_PORT = 5555;  
        final String IP = "127.0.0.1";  
        ExecutorService taskExecutor = Executors.newCachedThreadPool(Executors  
                .defaultThreadFactory());  
        // create asynchronous server socket channel bound to the default group  
        try (AsynchronousServerSocketChannel asynchronousServerSocketChannel = AsynchronousServerSocketChannel  
                .open()) {  
            if (asynchronousServerSocketChannel.isOpen()) {  
                // set some options  
                asynchronousServerSocketChannel.setOption(  
                        StandardSocketOptions.SO_RCVBUF, 4 * 1024);  
                asynchronousServerSocketChannel.setOption(  
                        StandardSocketOptions.SO_REUSEADDR, true);  
                // bind the server socket channel to local address  
                asynchronousServerSocketChannel.bind(new InetSocketAddress(IP,  
                        DEFAULT_PORT));  
                // display a waiting message while ... waiting clients  
                System.out.println("Waiting for connections ...");  
                while (true) {  
                    Future<AsynchronousSocketChannel> asynchronousSocketChannelFuture = asynchronousServerSocketChannel.accept();  
                    //使用CompletionHandler来处理IO事件  
//                  asynchronousServerSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>()   
                    //client使用CompletionHandler来处理IO事件  
                    //asynchronousSocketChannel.connect(new InetSocketAddress(IP, DEFAULT_PORT), null,new CompletionHandler<Void, Void>()   
                    try {  
                        final AsynchronousSocketChannel asynchronousSocketChannel = asynchronousSocketChannelFuture  
                                .get();  
                        Callable<String> worker = new Callable<String>() {  
                            @Override  
                            public String call() throws Exception {  
                                String host = asynchronousSocketChannel  
                                        .getRemoteAddress().toString();  
                                System.out.println("Incoming connection from: "  
                                        + host);  
                                final ByteBuffer buffer = ByteBuffer  
                                        .allocateDirect(1024);  
                                // transmitting data  
                                while (asynchronousSocketChannel.read(buffer)  
                                        .get() != -1) {  
                                    buffer.flip();  
                                }  
                                asynchronousSocketChannel.write(buffer).get();  
                                if (buffer.hasRemaining()) {  
                                    buffer.compact();  
                                } else {  
                                    buffer.clear();  
                                }  
                                asynchronousSocketChannel.close();  
                                System.out.println(host  
                                        + " was successfully served!");  
                                return host;  
                            }  
                        };  
                        taskExecutor.submit(worker);  
                    } catch (InterruptedException | ExecutionException ex) {  
                        System.err.println(ex);  
                        System.err.println("\n Server is shutting down ...");  
                        // this will make the executor accept no new threads  
                        // and finish all existing threads in the queue  
                        taskExecutor.shutdown();  
                        // wait until all threads are finished  
                        while (!taskExecutor.isTerminated()) {  
                        }  
                        break;  
                    }  
                }  
            } else {  
                System.out  
                        .println("The asynchronous server-socket channel cannot be opened!");  
            }  
        } catch (IOException ex) {  
            System.err.println(ex);  
        }  
  
    }  

}

// NIO2 递归遍历文件目录的接口实现
class ListTree extends SimpleFileVisitor<Path> {
	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
		System.out.println("Visited directory: " + dir.toString());
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) {
		System.out.println(exc);
		return FileVisitResult.CONTINUE;
	}
}
