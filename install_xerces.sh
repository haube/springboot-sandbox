#!/bin/sh
# Quelle: https://raw.githubusercontent.com/concretecms/concrete-cif/0a35b5d08792ebca58ea7065d720d25c88d2058f/bin/install-xerces-m2.sh


#		<dependency>
#			<groupId>xerces</groupId>
#			<artifactId>xercesImpl</artifactId>
#			<version>2.12.2</version>
#			<classifier>xml-schema-1.1</classifier>
#		</dependency>
#		<dependency>
#			<groupId>org.eclipse.wst.xml</groupId>
#			<artifactId>xpath2</artifactId>
#			<version>1.2.1</version> <!-- oder eine neuere Version -->
#		</dependency>
#		<dependency>
#			<groupId>com.ibm.icu</groupId>
#			<artifactId>icu4j</artifactId>
#			<version>4.2</version> <!-- oder eine neuere Version -->
#		</dependency>
#		<dependency>
#			<groupId>edu.princeton.cup</groupId>
#			<artifactId>java-cup</artifactId>
#			<version>10k</version> <!-- oder eine neuere Version -->
#		</dependency>
#		<dependency>
#			<groupId>xml-apis</groupId>
#			<artifactId>xml-apis</artifactId>
#			<version>1.4.02</version> <!-- oder eine neuere Version -->
#		</dependency>


set -o errexit
set -o nounset

# NOTE: These versions should be un sync with the ones of the /pom.xml and /bin/install-xerces-m2.bat files
XERCES_VERSION=2.12.2
XMLAPIS_VERSION=1.4.02
XPATH_VERSION=1.2.1
JAVACUP_VERSION=10k
ICU4J_VERSION=4.2

printf 'Checking environment... '
M2_PATH="$(mvn help:evaluate -Dexpression=settings.localRepository -q -DforceStdout)"
if true \
    && [ -f "$M2_PATH/xml-apis/xml-apis/$XMLAPIS_VERSION/xml-apis-$XMLAPIS_VERSION.jar" ] \
    && [ -f "$M2_PATH/xerces/xercesImpl/$XERCES_VERSION/xercesImpl-$XERCES_VERSION-xml-schema-1.1.jar" ] \
    && [ -f "$M2_PATH/org/eclipse/wst/xml/xpath2/$XPATH_VERSION/xpath2-$XPATH_VERSION.jar" ] \
    && [ -f "$M2_PATH/edu/princeton/cup/java-cup/$JAVACUP_VERSION/java-cup-$JAVACUP_VERSION.jar" ] \
    && [ -f "$M2_PATH/com/ibm/icu/icu4j/$ICU4J_VERSION/icu4j-$ICU4J_VERSION.jar" ] \
; then
    printf 'xerces is already installed.\n'
    exit 0
fi
printf 'we need to install xerces.\n'

printf 'Creating temporary directory... '
DOWNLOAD_DIR="$(mktemp -d)"
printf 'done.\n'

printf 'Downloading and extracting xerces... '
curl -sSLf "https://dlcdn.apache.org/xerces/j/binaries/Xerces-J-bin.$XERCES_VERSION-xml-schema-1.1.tar.gz" | tar -xz -C "$DOWNLOAD_DIR" --strip-components=1
printf 'done.\n'

printf 'Checking versions... '
checkJarVersion() {
    checkJarVersion_found="$(unzip -p "$DOWNLOAD_DIR/$1" META-INF/MANIFEST.MF | tr -d '\r' | grep -m 1 -E 'Bundle-Version:|Implementation-Version:' | cut -d' ' -f 2)"
    if [ "$2" != "$checkJarVersion_found" ]; then
        printf "The version of %s should be %s, but it's %s\n" "$1" "$2" "$checkJarVersion_found"
        exit 1
    fi
}
# We don't need to check the xerces version: it's already in the URL
# We don't need to check the xpath2 version: it's already in its file name
# We don't need to check the cuv version version: it's already in its file name
checkJarVersion xml-apis.jar "$XMLAPIS_VERSION"
checkJarVersion icu4j.jar "$ICU4J_VERSION"
printf 'done.\n'

printf 'Installing JARs to local m2 repository\n'

mvn install:install-file \
    -Dfile="$DOWNLOAD_DIR/xml-apis.jar" \
    -DgroupId=xml-apis \
    -DartifactId=xml-apis \
    -Dversion="$XMLAPIS_VERSION" \
    -Dpackaging=jar \
    -DgeneratePom=true

mvn install:install-file \
    -Dfile="$DOWNLOAD_DIR/xercesImpl.jar" \
    -DgroupId=xerces \
    -DartifactId=xercesImpl \
    -Dversion="$XERCES_VERSION" \
    -Dclassifier=xml-schema-1.1 \
    -Dpackaging=jar \
    -DgeneratePom=true

mvn install:install-file \
    -Dfile="$DOWNLOAD_DIR/org.eclipse.wst.xml.xpath2.processor_$XPATH_VERSION.jar" \
    -DgroupId=org.eclipse.wst.xml \
    -DartifactId=xpath2 \
    -Dversion="$XPATH_VERSION" \
    -Dpackaging=jar \
    -DgeneratePom=true

mvn install:install-file \
    -Dfile="$DOWNLOAD_DIR/cupv$JAVACUP_VERSION-runtime.jar" \
    -DgroupId=edu.princeton.cup \
    -DartifactId=java-cup \
    -Dversion="$JAVACUP_VERSION" \
    -Dpackaging=jar \
    -DgeneratePom=true

mvn install:install-file \
    -Dfile="$DOWNLOAD_DIR/icu4j.jar" \
    -DgroupId=com.ibm.icu \
    -DartifactId=icu4j \
    -Dversion="$ICU4J_VERSION" \
    -Dpackaging=jar \
    -DgeneratePom=true

printf 'Deleting temporary directory... '
rm -rf "$DOWNLOAD_DIR"
printf 'done.\n'
