package com.example.asahoo264.habitmeister1;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.interaxon.libmuse.ConnectionState;
import com.interaxon.libmuse.Muse;
import com.interaxon.libmuse.MuseManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.Formatter;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_print_interface;
import libsvm.svm_problem;

class svm_train {
    private svm_parameter param;
    private svm_problem prob;
    private svm_model model;
    private String input_file_name;
    private String model_file_name;
    private String error_msg;
    private int cross_validation;
    private int nr_fold;
    private static svm_print_interface svm_print_null = new svm_print_interface() {
        public void print(String var1) {
        }
    };

    svm_train() {
    }

    private static void exit_with_help() {
        System.out.print("Usage: svm_train [options] training_set_file [model_file]\noptions:\n-s svm_type : set type of SVM (default 0)\n\t0 -- C-SVC\t\t(multi-class classification)\n\t1 -- nu-SVC\t\t(multi-class classification)\n\t2 -- one-class SVM\n\t3 -- epsilon-SVR\t(regression)\n\t4 -- nu-SVR\t\t(regression)\n-t kernel_type : set type of kernel function (default 2)\n\t0 -- linear: u\'*v\n\t1 -- polynomial: (gamma*u\'*v + coef0)^degree\n\t2 -- radial basis function: exp(-gamma*|u-v|^2)\n\t3 -- sigmoid: tanh(gamma*u\'*v + coef0)\n\t4 -- precomputed kernel (kernel values in training_set_file)\n-d degree : set degree in kernel function (default 3)\n-g gamma : set gamma in kernel function (default 1/num_features)\n-r coef0 : set coef0 in kernel function (default 0)\n-c cost : set the parameter C of C-SVC, epsilon-SVR, and nu-SVR (default 1)\n-n nu : set the parameter nu of nu-SVC, one-class SVM, and nu-SVR (default 0.5)\n-p epsilon : set the epsilon in loss function of epsilon-SVR (default 0.1)\n-m cachesize : set cache memory size in MB (default 100)\n-e epsilon : set tolerance of termination criterion (default 0.001)\n-h shrinking : whether to use the shrinking heuristics, 0 or 1 (default 1)\n-b probability_estimates : whether to train a SVC or SVR model for probability estimates, 0 or 1 (default 0)\n-wi weight : set the parameter C of class i to weight*C, for C-SVC (default 1)\n-v n : n-fold cross validation mode\n-q : quiet mode (no outputs)\n");
        System.exit(1);
    }

    private void do_cross_validation() {
        int var2 = 0;
        double var3 = 0.0D;
        double var5 = 0.0D;
        double var7 = 0.0D;
        double var9 = 0.0D;
        double var11 = 0.0D;
        double var13 = 0.0D;
        double[] var15 = new double[this.prob.l];
        svm.svm_cross_validation(this.prob, this.param, this.nr_fold, var15);
        int var1;
        if(this.param.svm_type != 3 && this.param.svm_type != 4) {
            for(var1 = 0; var1 < this.prob.l; ++var1) {
                if(var15[var1] == this.prob.y[var1]) {
                    ++var2;
                }
            }

            System.out.print("Cross Validation Accuracy = " + 100.0D * (double)var2 / (double)this.prob.l + "%\n");
        } else {
            for(var1 = 0; var1 < this.prob.l; ++var1) {
                double var16 = this.prob.y[var1];
                double var18 = var15[var1];
                var3 += (var18 - var16) * (var18 - var16);
                var5 += var18;
                var7 += var16;
                var9 += var18 * var18;
                var11 += var16 * var16;
                var13 += var18 * var16;
            }

            System.out.print("Cross Validation Mean squared error = " + var3 / (double)this.prob.l + "\n");
            System.out.print("Cross Validation Squared correlation coefficient = " + ((double)this.prob.l * var13 - var5 * var7) * ((double)this.prob.l * var13 - var5 * var7) / (((double)this.prob.l * var9 - var5 * var5) * ((double)this.prob.l * var11 - var7 * var7)) + "\n");
        }

    }

    private void run(String[] var1) throws IOException {
        this.parse_command_line(var1);
        this.read_problem();
        this.error_msg = svm.svm_check_parameter(this.prob, this.param);
        if(this.error_msg != null) {
            System.err.print("ERROR: " + this.error_msg + "\n");
            System.exit(1);
        }

        if(this.cross_validation != 0) {
            this.do_cross_validation();
        } else {
            this.model = svm.svm_train(this.prob, this.param);
            svm.svm_save_model(this.model_file_name, this.model);
        }

    }

    public static void main(String[] var0) throws IOException {
        svm_train var1 = new svm_train();
        var1.run(var0);
    }

    private static double atof(String var0) {
        double var1 = Double.valueOf(var0).doubleValue();
        if(Double.isNaN(var1) || Double.isInfinite(var1)) {
            System.err.print("NaN or Infinity in input\n");
            System.exit(1);
        }

        return var1;
    }

    private static int atoi(String var0) {
        return Integer.parseInt(var0);
    }

    private void parse_command_line(String[] var1) {
        svm_print_interface var3 = null;
        this.param = new svm_parameter();
        this.param.svm_type = 0;
        this.param.kernel_type = 2;
        this.param.degree = 3;
        this.param.gamma = 0.0D;
        this.param.coef0 = 0.0D;
        this.param.nu = 0.5D;
        this.param.cache_size = 100.0D;
        this.param.C = 1.0D;
        this.param.eps = 0.0010D;
        this.param.p = 0.1D;
        this.param.shrinking = 1;
        this.param.probability = 0;
        this.param.nr_weight = 0;
        this.param.weight_label = new int[0];
        this.param.weight = new double[0];
        this.cross_validation = 0;

        int var2;
        for(var2 = 0; var2 < var1.length && var1[var2].charAt(0) == 45; ++var2) {
            ++var2;
            if(var2 >= var1.length) {
                System.out.print("In for loop.\n");
                        exit_with_help();
            }

            switch(var1[var2 - 1].charAt(1)) {
                case 'b':
                    this.param.probability = atoi(var1[var2]);
                    break;
                case 'c':
                    this.param.C = atof(var1[var2]);
                    break;
                case 'd':
                    this.param.degree = atoi(var1[var2]);
                    break;
                case 'e':
                    this.param.eps = atof(var1[var2]);
                    break;
                case 'f':
                case 'i':
                case 'j':
                case 'k':
                case 'l':
                case 'o':
                case 'u':
                default:
                    System.err.print("Unknown option: " + var1[var2 - 1] + "\n");
                    exit_with_help();
                    break;
                case 'g':
                    this.param.gamma = atof(var1[var2]);
                    break;
                case 'h':
                    this.param.shrinking = atoi(var1[var2]);
                    break;
                case 'm':
                    this.param.cache_size = atof(var1[var2]);
                    break;
                case 'n':
                    this.param.nu = atof(var1[var2]);
                    break;
                case 'p':
                    this.param.p = atof(var1[var2]);
                    break;
                case 'q':
                    var3 = svm_print_null;
                    --var2;
                    break;
                case 'r':
                    this.param.coef0 = atof(var1[var2]);
                    break;
                case 's':
                    this.param.svm_type = atoi(var1[var2]);
                    break;
                case 't':
                    this.param.kernel_type = atoi(var1[var2]);
                    break;
                case 'v':
                    this.cross_validation = 1;
                    this.nr_fold = atoi(var1[var2]);
                    if(this.nr_fold < 2) {
                        System.err.print("n-fold cross validation: n must >= 2\n");
                        exit_with_help();
                    }
                    break;
                case 'w':
                    ++this.param.nr_weight;
                    int[] var4 = this.param.weight_label;
                    this.param.weight_label = new int[this.param.nr_weight];
                    System.arraycopy(var4, 0, this.param.weight_label, 0, this.param.nr_weight - 1);
                    double[] var5 = this.param.weight;
                    this.param.weight = new double[this.param.nr_weight];
                    System.arraycopy(var5, 0, this.param.weight, 0, this.param.nr_weight - 1);
                    this.param.weight_label[this.param.nr_weight - 1] = atoi(var1[var2 - 1].substring(2));
                    this.param.weight[this.param.nr_weight - 1] = atof(var1[var2]);
            }
        }

        svm.svm_set_print_string_function(var3);
        if(var2 >= var1.length) {
            System.out.print("After loop.\n");
            exit_with_help();
        }

        this.input_file_name = var1[var2];
        if(var2 < var1.length - 1) {
            this.model_file_name = var1[var2 + 1];
        } else {
            int var6 = var1[var2].lastIndexOf(47);
            ++var6;
            this.model_file_name = var1[var2].substring(var6) + ".model";
        }

    }

    private void read_problem() throws IOException {
        BufferedReader var1 = new BufferedReader(new FileReader(this.input_file_name));
        Vector var2 = new Vector();
        Vector var3 = new Vector();
        int var4 = 0;

        while(true) {
            String var5 = var1.readLine();
            if(var5 == null) {
                this.prob = new svm_problem();
                this.prob.l = var2.size();
                this.prob.x = new svm_node[this.prob.l][];

                int var10;
                for(var10 = 0; var10 < this.prob.l; ++var10) {
                    this.prob.x[var10] = (svm_node[])var3.elementAt(var10);
                }

                this.prob.y = new double[this.prob.l];

                for(var10 = 0; var10 < this.prob.l; ++var10) {
                    this.prob.y[var10] = ((Double)var2.elementAt(var10)).doubleValue();
                }

                if(this.param.gamma == 0.0D && var4 > 0) {
                    this.param.gamma = 1.0D / (double)var4;
                }

                if(this.param.kernel_type == 4) {
                    for(var10 = 0; var10 < this.prob.l; ++var10) {
                        if(this.prob.x[var10][0].index != 0) {
                            System.err.print("Wrong kernel matrix: first column must be 0:sample_serial_number\n");
                            System.exit(1);
                        }

                        if((int)this.prob.x[var10][0].value <= 0 || (int)this.prob.x[var10][0].value > var4) {
                            System.err.print("Wrong input format: sample_serial_number out of range\n");
                            System.exit(1);
                        }
                    }
                }

                var1.close();
                return;
            }

            StringTokenizer var6 = new StringTokenizer(var5, " \t\n\r\f:");
            var2.addElement(Double.valueOf(atof(var6.nextToken())));
            int var7 = var6.countTokens() / 2;
            svm_node[] var8 = new svm_node[var7];

            for(int var9 = 0; var9 < var7; ++var9) {
                var8[var9] = new svm_node();
                var8[var9].index = atoi(var6.nextToken());
                var8[var9].value = atof(var6.nextToken());
            }

            if(var7 > 0) {
                var4 = Math.max(var4, var8[var7 - 1].index);
            }

            var3.addElement(var8);
        }
    }
}

class svm_predict {
    private static svm_print_interface svm_print_null = new svm_print_interface()
    {
        public void print(String s) {}
    };

    private static svm_print_interface svm_print_stdout = new svm_print_interface()
    {
        public void print(String s)
        {
            System.out.print(s);
        }
    };

    private static svm_print_interface svm_print_string = svm_print_stdout;

    static void info(String s)
    {
        svm_print_string.print(s);
    }

    private static double atof(String s)
    {
        return Double.valueOf(s).doubleValue();
    }

    private static int atoi(String s)
    {
        return Integer.parseInt(s);
    }

    private static void predict(BufferedReader input, DataOutputStream output, svm_model model, int predict_probability) throws IOException
    {
        int correct = 0;
        int total = 0;
        double error = 0;
        double sumv = 0, sumy = 0, sumvv = 0, sumyy = 0, sumvy = 0;

        int svm_type=svm.svm_get_svm_type(model);
        int nr_class=svm.svm_get_nr_class(model);
        double[] prob_estimates=null;

        if(predict_probability == 1)
        {
            if(svm_type == svm_parameter.EPSILON_SVR ||
                    svm_type == svm_parameter.NU_SVR)
            {
                svm_predict.info("Prob. model for test data: target value = predicted value + z,\nz: Laplace distribution e^(-|z|/sigma)/(2sigma),sigma="+svm.svm_get_svr_probability(model)+"\n");
            }
            else
            {
                int[] labels=new int[nr_class];
                svm.svm_get_labels(model,labels);
                prob_estimates = new double[nr_class];
                output.writeBytes("labels");
                for(int j=0;j<nr_class;j++)
                    output.writeBytes(" "+labels[j]);
                output.writeBytes("\n");
            }
        }
        while(true)
        {
            String line = input.readLine();
            if(line == null) break;

            StringTokenizer st = new StringTokenizer(line," \t\n\r\f:");

            double target = atof(st.nextToken());
            int m = st.countTokens()/2;
            svm_node[] x = new svm_node[m];
            for(int j=0;j<m;j++)
            {
                x[j] = new svm_node();
                x[j].index = atoi(st.nextToken());
                x[j].value = atof(st.nextToken());
            }

            double v;
            if (predict_probability==1 && (svm_type==svm_parameter.C_SVC || svm_type==svm_parameter.NU_SVC))
            {
                v = svm.svm_predict_probability(model,x,prob_estimates);
                output.writeBytes(v+" ");
                for(int j=0;j<nr_class;j++)
                    output.writeBytes(prob_estimates[j]+" ");
                output.writeBytes("\n");
            }
            else
            {
                v = svm.svm_predict(model,x);
                output.writeBytes(v+"\n");
            }

            if(v == target)
                ++correct;
            error += (v-target)*(v-target);
            sumv += v;
            sumy += target;
            sumvv += v*v;
            sumyy += target*target;
            sumvy += v*target;
            ++total;
        }
        if(svm_type == svm_parameter.EPSILON_SVR ||
                svm_type == svm_parameter.NU_SVR)
        {
            svm_predict.info("Mean squared error = "+error/total+" (regression)\n");
            svm_predict.info("Squared correlation coefficient = "+
                    ((total*sumvy-sumv*sumy)*(total*sumvy-sumv*sumy))/
                            ((total*sumvv-sumv*sumv)*(total*sumyy-sumy*sumy))+
                    " (regression)\n");
        }
        else {
            svm_predict.info("Accuracy = " + (double) correct / total * 100 +
                    "% (" + correct + "/" + total + ") (classification)\n");
            String accuracy = "/sdcard/accuracy";
            File file = new File(accuracy);
            FileOutputStream f = new FileOutputStream(file);

            String content = ("Accuracy = " + (double) correct / total * 100 + "% (" + correct + "/" + total + ") (classification)\n");
            byte[] b = content.getBytes();
            f.write(b);
        }
    }

    private static void exit_with_help()
    {
        System.err.print("usage: svm_predict [options] test_file model_file output_file\n"
                +"options:\n"
                +"-b probability_estimates: whether to predict probability estimates, 0 or 1 (default 0); one-class SVM not supported yet\n"
                +"-q : quiet mode (no outputs)\n");
        System.exit(1);
    }

    public static void main(String argv[]) throws IOException
    {
        int i, predict_probability=0;
        svm_print_string = svm_print_stdout;

        // parse options
        for(i=0;i<argv.length;i++)
        {
            if(argv[i].charAt(0) != '-') break;
            ++i;
            switch(argv[i-1].charAt(1))
            {
                case 'b':
                    predict_probability = atoi(argv[i]);
                    break;
                case 'q':
                    svm_print_string = svm_print_null;
                    i--;
                    break;
                default:
                    System.err.print("Unknown option: " + argv[i-1] + "\n");
                    exit_with_help();
            }
        }
        if(i>=argv.length-2)
            exit_with_help();
        try
        {
            BufferedReader input = new BufferedReader(new FileReader(argv[i]));
            DataOutputStream output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(argv[i+2])));
            svm_model model = svm.svm_load_model(argv[i+1]);
            if (model == null)
            {
                System.err.print("can't open model file "+argv[i+1]+"\n");
                System.exit(1);
            }
            if(predict_probability == 1)
            {
                if(svm.svm_check_probability_model(model)==0)
                {
                    System.err.print("Model does not support probabiliy estimates\n");
                    System.exit(1);
                }
            }
            else
            {
                if(svm.svm_check_probability_model(model)!=0)
                {
                    svm_predict.info("Model supports probability estimates, but disabled in prediction.\n");
                }
            }
            predict(input,output,model,predict_probability);
            input.close();
            output.close();
        }
        catch(FileNotFoundException e)
        {
            exit_with_help();
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            exit_with_help();
        }
    }
}

class svm_scale
{
    private String line = null;
    private double lower = -1.0;
    private double upper = 1.0;
    private double y_lower;
    private double y_upper;
    private boolean y_scaling = false;
    private double[] feature_max;
    private double[] feature_min;
    private double y_max = -Double.MAX_VALUE;
    private double y_min = Double.MAX_VALUE;
    private int max_index;
    private long num_nonzeros = 0;
    private long new_num_nonzeros = 0;


    private static void exit_with_help()
    {
        System.out.print(
                "Usage: svm-scale [options] data_filename\n"
                        +"options:\n"
                        +"-l lower : x scaling lower limit (default -1)\n"
                        +"-u upper : x scaling upper limit (default +1)\n"
                        +"-y y_lower y_upper : y scaling limits (default: no y scaling)\n"
                        +"-s save_filename : save scaling parameters to save_filename\n"
                        +"-r restore_filename : restore scaling parameters from restore_filename\n"
        );
        System.exit(1);
    }

    private BufferedReader rewind(BufferedReader fp, String filename) throws IOException
    {
        fp.close();
        return new BufferedReader(new FileReader(filename));
    }

    private void output_target(double value, DataOutputStream output)
    {
        if(y_scaling)
        {
            if(value == y_min)
                value = y_lower;
            else if(value == y_max)
                value = y_upper;
            else
                value = y_lower + (y_upper-y_lower) *
                        (value-y_min) / (y_max-y_min);
        }
        if(output!=null){
            try {
                output.writeBytes(value + " ");
            }
            catch (Exception e) {
                System.err.println("can't open output file ");
                System.exit(1);
            }
        }
        else
            System.out.print(value + " ");
    }

    private void output(int index, double value, DataOutputStream output )
    {
		// skip single-valued attribute
        if(feature_max[index] == feature_min[index])
            return;

        if(value == feature_min[index])
            value = lower;
        else if(value == feature_max[index])
            value = upper;
        else
            value = lower + (upper-lower) *
                    (value-feature_min[index])/
                    (feature_max[index]-feature_min[index]);

        if(value != 0)
        {
            if(output!=null){
                try {
                    output.writeBytes(index + ":" + value + " ");
                }
                catch (Exception e) {
                    System.err.println("can't open output file ");
                    System.exit(1);
                }
            }
            else
                System.out.print(index + ":" + value + " ");
            new_num_nonzeros++;
        }
    }

    private String readline(BufferedReader fp) throws IOException
    {
        line = fp.readLine();
        return line;
    }

    private void run(String []argv) throws IOException
    {
        int i,index;
        BufferedReader fp = null, fp_restore = null;
        String save_filename = null;
        String restore_filename = null;
        String data_filename = null;
        DataOutputStream output = null;

        for(i=0;i<argv.length;i++)
        {
            if (argv[i].charAt(0) != '-')	break;
            ++i;
            switch(argv[i-1].charAt(1))
            {
                case 'l': lower = Double.parseDouble(argv[i]);	break;
                case 'u': upper = Double.parseDouble(argv[i]);	break;
                case 'y':
                    y_lower = Double.parseDouble(argv[i]);
                    ++i;
                    y_upper = Double.parseDouble(argv[i]);
                    y_scaling = true;
                    break;
                case 's': save_filename = argv[i];	break;
                case 'r': restore_filename = argv[i];	break;
                default:
                    System.err.println("unknown option");
                    exit_with_help();
            }
        }

        if(!(upper > lower) || (y_scaling && !(y_upper > y_lower)))
        {
            System.err.println("inconsistent lower/upper specification");
            System.exit(1);
        }
        if(restore_filename != null && save_filename != null)
        {
            System.err.println("cannot use -r and -s simultaneously");
            System.exit(1);
        }

        if(argv.length != i+2)
            exit_with_help();

        data_filename = argv[i];
        try {
            fp = new BufferedReader(new FileReader(data_filename));
        } catch (Exception e) {
            System.err.println("can't open file " + data_filename);
            System.exit(1);
        }

        try {
            output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(argv[i + 1])));
        }catch (Exception e) {
            System.err.println("can't open output file ");
            System.exit(1);
        }
		// assumption: min index of attributes is 1
		// pass 1: find out max index of attributes
        max_index = 0;

        if(restore_filename != null)
        {
            int idx, c;

            try {
                fp_restore = new BufferedReader(new FileReader(restore_filename));
            }
            catch (Exception e) {
                System.err.println("can't open file " + restore_filename);
                System.exit(1);
            }
            if((c = fp_restore.read()) == 'y')
            {
                fp_restore.readLine();
                fp_restore.readLine();
                fp_restore.readLine();
            }
            fp_restore.readLine();
            fp_restore.readLine();

            String restore_line = null;
            while((restore_line = fp_restore.readLine())!=null)
            {
                StringTokenizer st2 = new StringTokenizer(restore_line);
                idx = Integer.parseInt(st2.nextToken());
                max_index = Math.max(max_index, idx);
            }
            fp_restore = rewind(fp_restore, restore_filename);
        }

        while (readline(fp) != null)
        {
            StringTokenizer st = new StringTokenizer(line," \t\n\r\f:");
            st.nextToken();
            while(st.hasMoreTokens())
            {
                index = Integer.parseInt(st.nextToken());
                max_index = Math.max(max_index, index);
                st.nextToken();
                num_nonzeros++;
            }
        }

        try {
            feature_max = new double[(max_index+1)];
            feature_min = new double[(max_index+1)];
        } catch(OutOfMemoryError e) {
            System.err.println("can't allocate enough memory");
            System.exit(1);
        }

        for(i=0;i<=max_index;i++)
        {
            feature_max[i] = -Double.MAX_VALUE;
            feature_min[i] = Double.MAX_VALUE;
        }

        fp = rewind(fp, data_filename);

		// pass 2: find out min/max value
        while(readline(fp) != null)
        {
            int next_index = 1;
            double target;
            double value;

            StringTokenizer st = new StringTokenizer(line," \t\n\r\f:");
            target = Double.parseDouble(st.nextToken());
            y_max = Math.max(y_max, target);
            y_min = Math.min(y_min, target);

            while (st.hasMoreTokens())
            {
                index = Integer.parseInt(st.nextToken());
                value = Double.parseDouble(st.nextToken());

                for (i = next_index; i<index; i++)
                {
                    feature_max[i] = Math.max(feature_max[i], 0);
                    feature_min[i] = Math.min(feature_min[i], 0);
                }

                feature_max[index] = Math.max(feature_max[index], value);
                feature_min[index] = Math.min(feature_min[index], value);
                next_index = index + 1;
            }

            for(i=next_index;i<=max_index;i++)
            {
                feature_max[i] = Math.max(feature_max[i], 0);
                feature_min[i] = Math.min(feature_min[i], 0);
            }
        }

        fp = rewind(fp, data_filename);

		// pass 2.5: save/restore feature_min/feature_max
        if(restore_filename != null)
        {
            // fp_restore rewinded in finding max_index
            int idx, c;
            double fmin, fmax;

            fp_restore.mark(2);				// for reset
            if((c = fp_restore.read()) == 'y')
            {
                fp_restore.readLine();		// pass the '\n' after 'y'
                StringTokenizer st = new StringTokenizer(fp_restore.readLine());
                y_lower = Double.parseDouble(st.nextToken());
                y_upper = Double.parseDouble(st.nextToken());
                st = new StringTokenizer(fp_restore.readLine());
                y_min = Double.parseDouble(st.nextToken());
                y_max = Double.parseDouble(st.nextToken());
                y_scaling = true;
            }
            else
                fp_restore.reset();

            if(fp_restore.read() == 'x') {
                fp_restore.readLine();		// pass the '\n' after 'x'
                StringTokenizer st = new StringTokenizer(fp_restore.readLine());
                lower = Double.parseDouble(st.nextToken());
                upper = Double.parseDouble(st.nextToken());
                String restore_line = null;
                while((restore_line = fp_restore.readLine())!=null)
                {
                    StringTokenizer st2 = new StringTokenizer(restore_line);
                    idx = Integer.parseInt(st2.nextToken());
                    fmin = Double.parseDouble(st2.nextToken());
                    fmax = Double.parseDouble(st2.nextToken());
                    if (idx <= max_index)
                    {
                        feature_min[idx] = fmin;
                        feature_max[idx] = fmax;
                    }
                }
            }
            fp_restore.close();
        }

        if(save_filename != null)
        {
            Formatter formatter = new Formatter(new StringBuilder());
            BufferedWriter fp_save = null;

            try {
                fp_save = new BufferedWriter(new FileWriter(save_filename));
            } catch(IOException e) {
                System.err.println("can't open file " + save_filename);
                System.exit(1);
            }

            if(y_scaling)
            {
                formatter.format("y\n");
                formatter.format("%.16g %.16g\n", y_lower, y_upper);
                formatter.format("%.16g %.16g\n", y_min, y_max);
            }
            formatter.format("x\n");
            formatter.format("%.16g %.16g\n", lower, upper);
            for(i=1;i<=max_index;i++)
            {
                if(feature_min[i] != feature_max[i])
                    formatter.format("%d %.16g %.16g\n", i, feature_min[i], feature_max[i]);
            }
            fp_save.write(formatter.toString());
            fp_save.close();
        }

		// pass 3: scale
        while(readline(fp) != null)
        {
            int next_index = 1;
            double target;
            double value;

            StringTokenizer st = new StringTokenizer(line," \t\n\r\f:");
            target = Double.parseDouble(st.nextToken());
            output_target(target, output);
            while(st.hasMoreElements())
            {
                index = Integer.parseInt(st.nextToken());
                value = Double.parseDouble(st.nextToken());
                for (i = next_index; i<index; i++)
                    output(i, 0, output);
                output(index, value, output);
                next_index = index + 1;
            }

            for(i=next_index;i<= max_index;i++)
                output(i, 0, output);
            if(output!=null){
                try {
                    output.writeBytes("\n");
                }
                catch (Exception e) {
                    System.err.println("can't open output file ");
                    System.exit(1);
                }
            }
            System.out.print("\n");
        }
        if (new_num_nonzeros > num_nonzeros)
            System.err.print(
                    "WARNING: original #nonzeros " + num_nonzeros+"\n"
                            +"         new      #nonzeros " + new_num_nonzeros+"\n"
                            +"Use -l 0 if many original feature values are zeros\n");

        fp.close();
    }

    public static void main(String argv[]) throws IOException
    {
        svm_scale s = new svm_scale();
        s.run(argv);
    }
}


public class FragmentFive extends Fragment implements View.OnClickListener {

    public static int id = 5;

    Button button1;
    Button button2;

    public static Fragment newInstance(Context context) {
    	FragmentFive f = new FragmentFive();
 
        return f;
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_five, null);
        Button button1 = (Button) root.findViewById(R.id.scale1);
        Button button2 = (Button) root.findViewById(R.id.scale2);
        Button button3 = (Button) root.findViewById(R.id.train);
        Button button4 = (Button) root.findViewById(R.id.test);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View v) {

        String[] scaling1 = {"-l", "-1", "-u", "1", "-s", "/sdcard/range1", "/sdcard/svmguide1"/*, ">"*/, "/sdcard/svmguide1.scale"};
        String[] scaling2 = {"-r", "/sdcard/range1", "/sdcard/svmguide1.t"/*, ">"*/, "/sdcard/svmguide1.t.scale"};
        String[] training = {"/sdcard/svmguide1", "/sdcard/svmguide1.model"};
        String[] training2 = {"/sdcard/svmguide1.scale", "/sdcard/svmguide1.scale.model"};
        String[] testing = {"/sdcard/svmguide1.t", "/sdcard/svmguide1.model", "/sdcard/svmguide1.out"};
        String[] testing2 = {"/sdcard/svmguide1.t.scale", "/sdcard/svmguide1.scale.model", "/sdcard/svmguide1.scale.out"};

        if (v.getId() == R.id.scale1) {
            //svm_problem prob = new svm_problem();
            try {
                //svm_problem prob = new svm_problem();
                TextView scaling_f = (TextView) (getActivity()).findViewById(R.id.scaling1);
                scaling_f.setText("scaling input file started...");
                svm_scale.main(scaling1);
                scaling_f.setText("scaling input file finished");
                //svm_predict.main(testing);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //TextView training_f = (TextView) (getActivity()).findViewById(R.id.training_f);
            //training_f.setText("training finished");

        }
        else if (v.getId() == R.id.scale2) {
            //svm_problem prob = new svm_problem();
            try {
                //svm_problem prob = new svm_problem();
                TextView scaling_f = (TextView) (getActivity()).findViewById(R.id.scaling2);
                scaling_f.setText("scaling test file started...");
                svm_scale.main(scaling2);
                scaling_f.setText("scaling test file finished");
                //svm_predict.main(testing);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //TextView training_f = (TextView) (getActivity()).findViewById(R.id.training_f);
            //training_f.setText("training finished");

        }
        else if (v.getId() == R.id.train) {
            //svm_problem prob = new svm_problem();
            try {
                //svm_problem prob = new svm_problem();
                TextView training_f = (TextView) (getActivity()).findViewById(R.id.training);
                training_f.setText("training started...");
                svm_train.main(training2);
                training_f.setText("training finished");
                //svm_predict.main(testing);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //TextView training_f = (TextView) (getActivity()).findViewById(R.id.training_f);
            //training_f.setText("training finished");


        }
        else if (v.getId() == R.id.test) {
            //svm_problem prob = new svm_problem();
            try {
                //svm_problem prob = new svm_problem();
                TextView testing_f = (TextView) (getActivity()).findViewById(R.id.testing);
                TextView result = (TextView) (getActivity()).findViewById(R.id.result);
                testing_f.setText("testing started...");
                svm_predict.main(testing2);
                testing_f.setText("testing finished");
                String accuracy = "/sdcard/accuracy";
                BufferedReader input = null;
                try
                {
                    input = new BufferedReader(new FileReader(accuracy));
                }catch (Exception e) {
                    System.err.println("can't open input file ");
                    System.exit(1);
                }

                String input_ = input.readLine();
                System.out.println(input_);
                result.setText("print result: " + input_);
                //svm_predict.main(testing);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            Activity a = getActivity();
            if(a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
}