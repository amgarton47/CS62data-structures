import contextlib
import fnmatch
import glob
import shutil
import tempfile
import os
import grp

@contextlib.contextmanager
def make_temp_directory():
    """ Creates a temporary directory in context and remove once complete.
    @yield: directory path
    """
    temp_dir = tempfile.mkdtemp()
    yield temp_dir
    shutil.rmtree(temp_dir)


def wildcard_copy(dir, dest, ignores=None):
    """ Copy all the files contained within a directory to another directory.

    @param dir: source directory
    @param dest: destination directory
    @param ignores: NOT IMPLEMENTED
    @return: list of copied files, possibly empty
    """
    files = glob.glob(os.path.join(dir, '*'))
    copied = []
    for i in files:
        if os.path.isfile(i):
            shutil.copy(i, dest)
            copied.append(os.path.join(dest, os.path.basename(i)))

    return copied


def find_ext(temp_src, ext):
    """ Walk a directory for files ending in .java
    @param matches: output param list of files
    @param temp_src: top-level directoy to be searched
    @return: matches
    """
    matches = []
    for root, dirnames, filenames in os.walk(temp_src):
        for filename in fnmatch.filter(filenames, ext):
            matches.append(os.path.join(root, filename))
    return matches

def fix_permissions(target):
    """
    Takes a file/directory name and sets full permissions for owner and group
    (chmod 770) as well as setting the group to cs062. If either operation
    fails, it prints a warning but otherwise does nothing.
    """
    try:
      os.chmod(target, 0770) 
    except:
      print("Warning: Couldn't change permissions of '{}'".format(target))
    try:
      os.chown(target, os.getuid(), grp.getgrnam("cs062").gr_gid)
    except:
      print("Warning: Couldn't change ownership of '{}'".format(target))
