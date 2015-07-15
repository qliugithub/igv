package org.broad.igv.feature;

import org.broad.igv.track.WindowFunction;
import org.broad.igv.util.collections.MultiMap;

import java.awt.*;
import java.util.List;

/**
 * Representation of a feature from a UCSC "snp" file
 *
 * @author jrobinso
 *         Date: 11/5/13
 *         Time: 1:11 PM
 */
public class UCSCSnpFeature implements IGVFeature, htsjdk.tribble.Feature {

    String chr;
    int start;
    int end;
    float score;
    Strand strand;
    String name;
    String observed;
    String molType;
    String snpClass;
    String function;
    String submitters;
    String alleles;
    String alleleFreqs;

    public UCSCSnpFeature(String chr, int start, int end, String[] tokens) {
        this.chr = chr;
        this.start = start;
        this.end = end;
        this.name = tokens[4];
        this.score = tokens[5].equals(".") ? 1000 : Float.parseFloat(tokens[5]);
        this.strand = tokens[6].equals("+") ? Strand.POSITIVE : (tokens[6].equals("-") ? Strand.NEGATIVE : Strand.NONE);
        this.observed = tokens[9];
        this.molType = tokens[10];
        this.snpClass = tokens[11];
        this.function = tokens[15];
        this.submitters = tokens[20];
        this.alleles = tokens[22];
        this.alleleFreqs = tokens[24];
    }

    @Override
    public String getChr() {
        return chr;
    }

    @Override
    public int getStart() {
        return start;
    }

    @Override
    public int getEnd() {
        return end;
    }

    @Override
    public String getContig() {
        return chr;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getScore() {
        return this.score;
    }


    @Override
    public String getValueString(double position, WindowFunction windowFunction) {
        return getDescription();
    }

    @Override
    public String getDescription() {

        StringBuffer desc = new StringBuffer();

        if (end - start > 1) {
            desc.append(chr + ":" + (start + 1) + "-" + end);
        } else {
            desc.append(chr + ":" + (start + 1));
        }

        desc.append("<br><b>Name:</b> " + name);
        desc.append("<br><b>Observed:</b> " + observed);
        desc.append("<br><b>Mol type:</b> " + molType);
        desc.append("<br><b>Class:</b> " + snpClass);
        desc.append("<br><b>Function:</b> " + function);
        desc.append("<br><b>Alleles:</b> " + alleles.replace(",", ", "));
        desc.append("<br><b>Allele freqs:</b> " + alleleFreqs.replace(",", ", "));
        desc.append("<br><b>Submitters:</b> " + submitters.replace(",", ", "));
        return desc.toString();
    }


    // Everything below has to be implemented for IGVFeature.   Sigh....


    @Override
    public void setStart(int start) {
        this.start = start;
    }

    @Override
    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public String getType() {
        return "SNP";
    }

    @Override
    public String getIdentifier() {
        return name;
    }

    @Override
    public Strand getStrand() {
        return strand;
    }

    @Override
    public int getLength() {
        return end - start;
    }

    @Override
    public MultiMap<String, String> getAttributes() {
        return null;
    }

    @Override
    public boolean contains(IGVFeature feature) {
        if (feature == null) {
            return false;
        }
        if (!this.getChr().equals(feature.getChr()) ||
                this.getStrand() != feature.getStrand()) {
            return false;
        }
        if ((feature.getStart() >= this.getStart()) && (feature.getEnd() <= this.getEnd())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean contains(double location) {
        return location >= start && location <= end;
    }

    @Override
    public List<Exon> getExons() {
        return null;
    }

    @Override
    public Color getColor() {
        return Color.black;
    }

    @Override
    public String getURL() {
        return null;
    }

}