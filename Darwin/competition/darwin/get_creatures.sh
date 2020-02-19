#!/bin/sh

if [ $# -lt 1 ]
then
  echo "Must supply a source directory."
  exit 1
fi

src="$1"
dst=creatures

field=`echo $src | tr '/' ' ' | wc -w`

out=copy.sh

if [ -f $out ]
then
  echo -n "Script '$out' exists. Replace it? (y/N) "
read ans
if [ ! $ans ]
then
  ans="n"
fi
case "$ans" in
  y|yes|Y|Yes|YES)
    echo "Overwriting old '$out'."
    echo "" > copy.sh
    ;;
  *)
    echo "Aborting."
    exit 1
    ;;
esac
fi

for file in `find "$src" -name "myspecies.txt"`
do
  target=`echo $file | cut -d/ -f "$field"- | cut -d/ -f2`.txt
  echo "Getting $file as $target ..."
  grep "$target" "$out" 2>/dev/null 1>/dev/null
  if [ -f "$dst/$target" -o $? -eq 0 ]
  then
    old="$dst/$target"
    if [ ! -f "$old" ]
    then
      old=`grep "^cp.*$target" "$out" | tail -n 1 | cut -d" " -f 2 | cut -d\" -f 2`
    fi
    echo "  ... repeat file ..."
    diff "$file" "$old"
    if [ $? -ne 0 ]
    then
      echo -n "  ... file is different. Replace old version? (y/N) "
      read ans
      if [ ! $ans ]
      then
        ans="n"
      fi
      case "$ans" in
        y|yes|Y|Yes|YES)
          echo "  ... adding copy command for new version."
          echo "cp \"$file\" \"$dst/$target\"" >> $out
          echo "chmod 775 \"$dst/$target\"" >> $out
          ;;
        *)
          echo "  ... ignoring new version."
          ;;
      esac
    else
      echo "  ... files are the same. Ignoring duplicate."
    fi
  else
    echo "  ... new file. Adding copy command."
    echo "cp \"$file\" \"$dst/$target\"" >> $out
    echo "chmod 775 \"$dst/$target\"" >> $out
  fi
done
