
[//]: # ([![Version]&#40;https://img.shields.io/github/v/release/SaedSilva/GeneWhale?sort=semver&display_name=release&label=alpha&color=brightgreen&#41;]&#40;&#41;)

[//]: # ([![Github All Releases]&#40;https://img.shields.io/github/downloads/SaedSilva/GeneWhale/total.svg&#41;]&#40;&#41;)

<div align="center">
    <img src="GeneWhaleNoBg.png" alt="gene whale logo" width="128" height="128"/>
    <h1 align="center">GeneWhale: A Utility Tool for Pan-Genome Analysis Using Docker</h1>
</div>

![License: AGPL v3](https://img.shields.io/badge/License-AGPL%20v3-blue.svg)

Software made with:

![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

Operational systems available:

![Windows](https://img.shields.io/badge/Windows-0078D6?style=for-the-badge&logo=windows&logoColor=white)
![Linux](https://img.shields.io/badge/Linux-FCC624?style=for-the-badge&logo=linux&logoColor=black)
![macOS](https://img.shields.io/badge/mac%20os-000000?style=for-the-badge&logo=macos&logoColor=F0F0F0)

---

### WARNING

**This project is still in development.**
**The current version is not fully functional.**
**And the release download is not available yet.**
**Probably project name will change.**
**For use at this moment, please use the source code with jdk 21.**

Clone this project:

```bash
git clone https://github.com/SaedSilva/GeneWhale.git
```

Create a package for your OS using the following command:

```bash
./gradlew build composeApp:packageDistributionForCurrentOS
```

Or create a jar file using the following command:

```bash
./gradlew build composeApp:packageUberJarForCurrentOS
```

---

GeneWhale is a utility tool designed to simplify the analysis of pan-genomes by acting as a wrapper for commonly used
tools, leveraging Docker for seamless execution.

### Requirements

To use GeneWhale, you must have [Docker](https://docs.docker.com/get-docker/) installed on your machine.  
Additionally, Docker must be running (i.e., the Docker daemon must be active).  
To verify if Docker is running, use the following command:

```bash
docker info
```

- If Docker is running, you will see detailed information about your Docker configuration.
- If it is not running, you will see an error message indicating that the Docker daemon is not active.

### Supported Platforms

GeneWhale is available for **Windows**, **Linux**, and **macOS**.

You can download the appropriate version for your operating system from the **Releases** section.

### Running the Tool

We recommend using the `.jar` file if you already have **Java 21 or higher** installed on your system.  
You can download the latest JDK from the official website:  
[https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)

Alternatively, you can use the installer provided for your operating system.

---

### Pan genomes Tools progress:

Marked tool is minimally functional and can be used for pan-genome analysis.
The progress bar indicates the percentage of addition of the tool's parameters and functionalities.

- [X] Panaroo ![progress](https://progress-bar.xyz/25/)
- [ ] Roary ![progress](https://progress-bar.xyz/0/)
- [ ] Ppanggolin ![progress](https://progress-bar.xyz/0/)
- [ ] Pirate ![progress](https://progress-bar.xyz/0/)
- [ ] Peppan ![progress](https://progress-bar.xyz/0/)
- [ ] Pandelos ![progress](https://progress-bar.xyz/0/)
- [ ] Panacota ![progress](https://progress-bar.xyz/0/)
- [ ] Ribap ![progress](https://progress-bar.xyz/0/)
- [ ] Pato ![progress](https://progress-bar.xyz/0/)
- [ ] Micropan ![progress](https://progress-bar.xyz/0/)
- [ ] GET_HOMOLOGUES ![progress](https://progress-bar.xyz/0/)
- [ ] PanCake ![progress](https://progress-bar.xyz/0/)
- [ ] PGAP-X ![progress](https://progress-bar.xyz/0/)
- [ ] PanFunPro ![progress](https://progress-bar.xyz/0/)
- [ ] SplitMEM ![progress](https://progress-bar.xyz/0/)
- [ ] Panakeia ![progress](https://progress-bar.xyz/0/)
- [ ] PANPROVA ![progress](https://progress-bar.xyz/0/)
- [ ] BGDMdocker ![progress](https://progress-bar.xyz/0/)
- [ ] DeNoGAP ![progress](https://progress-bar.xyz/0/)
- [ ] Harvest ![progress](https://progress-bar.xyz/0/)
- [ ] NGSPanPipe ![progress](https://progress-bar.xyz/0/)
- [ ] PanACEA ![progress](https://progress-bar.xyz/0/)
- [ ] PANINI ![progress](https://progress-bar.xyz/0/)
- [ ] PanOCT ![progress](https://progress-bar.xyz/0/)
- [ ] Panseq ![progress](https://progress-bar.xyz/0/)
- [ ] PanTetris ![progress](https://progress-bar.xyz/0/)
- [ ] Spine e AGEnt ![progress](https://progress-bar.xyz/0/)
- [ ] Pyseer ![progress](https://progress-bar.xyz/0/)
- [ ] Seq-pan-seq ![progress](https://progress-bar.xyz/0/)
- [ ] Ptolemy ![progress](https://progress-bar.xyz/0/)


### License
This project is licensed under the GNU Affero General Public License v3.0 (AGPL-3.0).
You can find the full license text in the [LICENSE](LICENSE) file.


### Contributors
![Contributors](https://contrib.rocks/image?repo=saedsilva/genewhale)
