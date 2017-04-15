package com.jcourse.kladov;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v1.DbxEntry;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.DbxPathV2;
import com.dropbox.core.v2.files.*;
import com.dropbox.core.v2.users.FullAccount;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.TreeMap;

public class DropboxDataProvider implements DataProvider {
	DbxClientV2 client;
	DbxRequestConfig config;

	public DropboxDataProvider(String accessToken) {
		// Create Dropbox client
		config = new DbxRequestConfig("Java-Developer/1.0", "en_US");
		client = new DbxClientV2(config, accessToken);
	}

	class FileImpl extends AbstractFile {
		Metadata metadata;

		FileImpl(String path) {
			super(path);

			if (isRoot())
				return; // dropbox doesn't support metadata on root

			try {
				metadata = client.files().getMetadata(path);
			}
			catch (GetMetadataErrorException e) {
				if (e.errorValue.isPath()) {
					LookupError le = e.errorValue.getPathValue();
					if (le.isNotFound()) {
						return;
					}
				}
				System.err.println("GetMetadataErrorException: " + e.toString());
				return;
			}
			catch (DbxException e) {
				System.err.println("DbxException: " + e.toString());
				return;
			}
		}
		FileImpl(String path, Metadata metadata) {
			super(path);
			this.metadata = metadata;
		}

		@Override
		long getLength() {
			return isDirectory() ? 0 : ((FileMetadata) metadata).getSize();
		}

		@Override
		long getModificationTime() {
			if (isDirectory()) {
				return 0; // dropbox don't return client_modified information about folders, only files.
			}
			return ((FileMetadata) metadata).getClientModified().getTime();
		}

		@Override
		boolean isExists() {
			return isRoot() || metadata != null;
		}

		@Override
		boolean isFile() {
			return metadata instanceof FileMetadata;
		}

		@Override
		boolean isDirectory() {
			return isRoot() || metadata instanceof FolderMetadata;
		}

		boolean isRoot() {
			return path == null || path.length() == 0;
		}

		@Override
		AbstractFile[] getChildren() {
			ListFolderResult result;
			TreeMap<String,Metadata> children = new TreeMap<>();

			try {
				try {
					result = client.files().listFolder(path);
				}
				catch (ListFolderErrorException e) {
					System.err.println("ListFolderErrorException: " + e.toString());
					return new AbstractFile[0];
				}

				while (true) {
					for (Metadata md : result.getEntries()) {
						if (md instanceof DeletedMetadata) {
							children.remove(md.getPathLower());
						} else {
							children.put(md.getPathLower(), md);
						}
					}

					if (!result.getHasMore()) break;

					try {
						result = client.files().listFolderContinue(result.getCursor());
					}
					catch (ListFolderContinueErrorException e) {
						System.err.println("ListFolderErrorException: " + e.toString());
						return new AbstractFile[0];
					}
				}
			}
			catch (DbxException e) {
				System.err.println("DbxException: " + e.toString());
				return new AbstractFile[0];
			}

			AbstractFile[] files = new AbstractFile[children.size()];

			int[] i = {0};
			children.forEach((k, v) -> files[i[0]++] = new FileImpl(k, v));

			return files;
		}

		@Override
		AbstractFile getParent() {
			return isRoot() ? null : new FileImpl(DbxPathV2.getParent(path));
		}

		@Override
		AbstractFile getChild(String name) {
			// Note: dropbox doesn't support paths started with /
			return new FileImpl(isRoot() ? name : path + "/" + name);
		}

		@Override
		InputStream getContent() {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			try {
				FileMetadata metadata = client.files()
						.downloadBuilder(path)
						.download(byteArrayOutputStream);
			} catch (DbxException e) {
				System.err.println("DbxException: " + e.toString());
			} catch (IOException e) {
				System.err.println("IOException: " + e.toString());
			}
			return new ByteInputStream(byteArrayOutputStream.toByteArray(), byteArrayOutputStream.size());
		}

		@Override
		String getName() {
			return metadata == null ? "" : metadata.getName();
		}
	}

	@Override
	public AbstractFile getFile(String path) {
		return new FileImpl(path);
	}

	@Override
	public String getSeparator() {
		return "/";
	}
}
