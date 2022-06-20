jXPDL
=====

This is a mirror of https://sourceforge.net/projects/jxpdl/ created with the following commands::

   echo "sasaboy = Sasa Bojanic <sasaboy@together.at>" > authors-transform.txt
   git svn init --trunk=trunk --tags=tags --tags=tags/releases --branches=branches svn://svn.code.sf.net/p/jxpdl/code/
   git config svn.authorsfile authors-transform.txt
   git svn fetch
   git remote add origin git@github.com:Shoobx/jXPDL.git
   git branch -r | grep origin/txm | sed -e 's#.*/##' | while read br; do git switch $br; done
   git switch master
   git for-each-ref --format="%(refname:short) %(objectname)" refs/remotes/origin/tags |  cut -d / -f 3- | while read ref; do git tag $ref; done
   git tag -d releases
   git push origin --all
   git push origin --tags
