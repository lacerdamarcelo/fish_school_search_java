package functions;

import java.util.ArrayList;

public class Griewank implements Function {
	
	@Override
	public double getFitness(double[] parameters) {
		double termoSomatorio = 0.0;
		double termoProdutorio = 1;
		for (int i = 0; i < parameters.length; i++){
			termoSomatorio+= parameters[i]*parameters[i];
			
			//esse i+1 eh por causa do NaN causado por divisao por zero, coisas do java que come�ar a contar do zero
			termoProdutorio*= Math.cos(parameters[i]/Math.sqrt(i+1));
		}
		termoSomatorio = termoSomatorio/4000;
		return 1 + termoSomatorio - termoProdutorio;
	}

	@Override
	public ArrayList<double[]> getOptimalSolutions(double dimMinValue,
			double dimMaxValue, int numDim) {
		double[][] solutionValues = new double[][] {{-28.27905178172661,-26.701421150921426},{21.992400400681486,-8.893170439740928},{-12.560756092336304,22.266881801197272},{-6.335491236341245,-4.466807289687608},{18.93760476872185,22.113803206923603},{3.248458502358934,-17.773777381819208},{-18.833637264959275,13.329554055961081},{-18.848171160244217,-13.363144505304716},{6.358609817782411,22.31628718315341},{18.84789535802812,-22.186316090355632},{-3.1206659334568,-0.00660736917096307},{-9.418009107459614,-17.792278160323555},{28.287380502983694,8.931812842606499},{12.625034874426238,-4.4604152376966395},{-22.02174101403649,26.648019239104897},{-25.127109246913516,-4.483139508981867},{9.398786956427097,26.740579253036348},{25.173078058713678,-13.43887274452425},{0.07364214102951484,-22.19145200091213},{22.022284313394472,0.023752673123013223},{6.2890295888783205,-13.345620872494147},{15.788252560436387,-0.08895394439619667},{-6.276381776201211,22.283298987847992},{-12.550816004229066,-4.401356775652581},{22.00024716592348,17.930323172706412},{9.41694842972687,-26.667960767968534},{-25.18159623882723,4.448370806736701},{-25.09951884676717,-13.376981836913147},{9.418668421692898,17.77732956102456},{-12.378509194374306,4.309862921353565},{12.546682044925472,13.331388744187203},{0.028961404272832492,-4.4984708583103},{-18.925936812690246,22.180136974559886},{-15.662341114479403,-26.686126552602605},{6.280414497584302,4.309429084804345},{18.785973521485968,-13.169816364407868},{0.037549091097690536,22.22144733423863},{-9.401630783054026,-8.948451204382271},{28.306612212351265,26.654747141931335},{9.447088199444856,-17.833952692249284},{-22.014699006013434,8.803582337250091},{15.716095148351041,8.898313576642858},{25.174244297503478,-4.425790857567054},{-9.430722681277595,26.6757764995379},{-3.1752157222862194,-8.882214152084634},{6.297387464862392,-22.228060387136857},{-18.838876446651618,4.365828850229194},{-22.00528150799469,-0.039542194483323606},{15.722899366073284,-17.695873341019436},{-6.2860823474675795,13.282317856856631},{22.00333182674869,8.889419558625036},{9.394874512608217,-8.861301925259985},{-28.2271761945874,17.830333798148313},{-28.261462738162617,0.06421853046942579},{25.12717638461592,-22.228604829009832},{-9.435348921995539,17.781889173329855},{-9.432645031493566,-26.665979733281517},{18.724163799483534,4.388111520203484},{3.1243904511804277,-8.830184284517477},{-21.992999750520802,17.72967175474515},{9.43242451715487,8.882189232757476},{-3.137291246213597,26.690236483892896},{-12.685719605868938,-13.346166742397124},{25.140630352497965,22.355570831390057},{15.751708531255879,-26.65805622099559},{9.42985691602442,0.0025118558107854743},{21.991830500461887,26.691173540188004},{-15.729928641562289,8.905205362487656},{-0.023921753399895742,13.28593651868126},{-25.124521152269782,22.243853399090433},{-25.1544932428046,-22.107309822809455},{18.88115642350637,-4.506582234366301},{-6.251318900189665,-13.30145640319085},{15.714279573831016,17.81224748045557},{3.1429286041638766,-26.76010705669929},{-15.674128535841714,-8.773763785674445},{3.325137933525259,26.748016938660754},{25.099192811234204,4.410838662640441},{12.577853178820643,-13.35065214836469},{12.589557592764654,22.197789726820044},{-9.417486750165828,0.004688971630163495},{0.025372920521808184,-13.287898407812097},{6.316762034761691,-4.399776487837164},{-22.018387134082072,-26.692109592049658},{15.721212635684045,-8.917653278545632},{-28.298068484854884,8.918145931898122},{-28.32470216399486,-17.740339611568704},{28.275487055977074,-26.750522592013397},{18.89095218539898,13.382281218185017},{-3.1496752008851603,17.77326245321768},{12.602537770342819,-22.13898766856991},{-22.006267695109106,-17.787054004357994},{28.294051446178035,-0.006934506472193465},{-18.85997437506016,-4.463989960692819},{-3.411740909686681,8.836528069543391},{-12.579161241145425,-22.23339719037008},{22.028489412896565,-17.73647977173422},{15.592453857524816,26.781917035126384},{3.1335865475588114,17.76151698510733},{21.97969939766687,-26.63954940893945},{-0.14734819534194865,4.717609512629805},{25.09993929233507,13.237859441961056},{28.289476149811602,-17.778154744303453},{-12.649199698573703,13.107864380845736},{3.1616744534964663,8.94180257318298},{-22.02763733216903,-8.857838712918038},{-3.1532610434378507,-17.808012913461912},{-18.91711401040549,-22.412334647390114},{-28.30573893263826,-8.884165870460375},{-9.469654425106297,8.925839273900495},{12.547999697152648,4.423684382067706},{6.23301422972767,13.377315269684434},{28.296356664456827,17.7905848227783},{28.338974169594934,-8.950837364277431},{-28.30347171245358,26.734975883118842},{-6.317797326971862,-22.143785521332774},{-15.732639854929147,-17.83676430596466},{-6.269705828814801,4.3572239503352845},{-3.0688862263036536,-26.527386098742106},{-15.70871337986419,17.788175784713484},{-25.134437066881038,13.359328592109263},{3.2102376502027123,0.14639358584613513},{-15.79005622631325,26.734436098237637},{-15.654873723354848,-0.08323775827408524}};
		ArrayList<double[]> ret = new ArrayList<double[]>();
		for(int i=0;i<solutionValues.length;i++){
			ret.add(solutionValues[i]);
		}
		return ret;
	}

}