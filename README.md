# atom
A very basic Java library for dealing with atom xml feeds

To perform a release with the current configuration,
and starting with a snapshot that is ready for release:

$ mvn release:clean release:prepare -P release
$ mvn release:perform -P release
