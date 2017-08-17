package eu.paasage.upperware.profiler.generator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@Slf4j
@EnableAsync
public class GeneratorRestApp {


    public static void main(String[] args) {

        log.info("                                                                                                              \n" +
                "                                                                                                              \n" +
                "         o                   ato                                                                              \n" +
                "     erato                nerator                                                      rCp                    \n" +
                "    nera      e era     Gene         nerato    en rato    enerat     en ra   CpGene    rCpG    atorC    er to \n" +
                "   ener      Generat   pGen  ator   ene  tor  Generato   Gen  ato   Genera  rCpGene  torCpG  eratorCp  nerato \n" +
                "   ene       Gen rat   pGen rator  Gene ator  Gen  ato  pGen rato   Gene       Gene   orCp  nera  rCp  nera   \n" +
                "  Gene      pGe   ato CpGen   tor  Generator  Gen  ato  pGenerato   Gen    orCpGene   orC   ner   rCp  ner    \n" +
                "  Gener     pGe  rat  CpGen  atorCpGen       pGen  ato CpGe        pGen   torC Gene  torC   ner  orCp ener    \n" +
                "  Generato  pGenerat  CpGeneratorCpGene ator pGe  rato CpGen rato  pGen  atorC Gene  torCp eneratorCp ener    \n" +
                "   enerato CpGenerat   pGenerator  GeneratorCpGe  rato  pGenerato  pGen  atorCpGene  torCp  neratorC  ener    \n" +
                "    nerato CpG nera     Generator   enerato CpGe  rato   Generat  CpGen   torCpGene  torCp  nerator  Gener    \n" +
                "          rCpG                                                                                                \n" +
                "          rCpG                                                                                                \n" +
                "                                                                                                              \n");


        log.info("GeneratorRestApp is starting...");

        SpringApplication.run(GeneratorRestApp.class, args);

        log.info("GeneratorRestApp started...");
    }
}


