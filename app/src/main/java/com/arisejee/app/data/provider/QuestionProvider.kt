package com.arisejee.app.data.provider

import com.arisejee.app.data.db.entity.QuestionEntity

interface QuestionProvider {
    suspend fun fetchQuestions(subject: String, chapter: String, examType: String, count: Int = 15): List<QuestionEntity>
}

class RemoteQuestionProvider : QuestionProvider {
    override suspend fun fetchQuestions(subject: String, chapter: String, examType: String, count: Int): List<QuestionEntity> {
        // TODO: Connect to your API here.
        // Return emptyList as placeholder; the app falls back to local data.
        return emptyList()
    }
}

class LocalQuestionProvider : QuestionProvider {
    override suspend fun fetchQuestions(subject: String, chapter: String, examType: String, count: Int): List<QuestionEntity> {
        val key = "${'$'}subject|${'$'}chapter"
        return (sampleBank[key] ?: defaultQuestions(subject, chapter)).filter { it.examType == examType }.take(count)
    }

    private fun q(sub: String, ch: String, text: String, a: String, b: String, c: String, d: String, ans: Int, sol: String, diff: String, exam: String) =
        QuestionEntity(subject=sub, chapter=ch, questionText=text, optionA=a, optionB=b, optionC=c, optionD=d, correctAnswer=ans, solution=sol, difficulty=diff, examType=exam)

    private fun defaultQuestions(sub: String, ch: String): List<QuestionEntity> = listOf(
        q(sub,ch,"Sample question 1 for ${'$'}ch","Option A","Option B","Option C","Option D",0,"Solution: A is correct.","EASY","JEE_MAINS"),
        q(sub,ch,"Sample question 2 for ${'$'}ch","Option A","Option B","Option C","Option D",1,"Solution: B is correct.","EASY","JEE_MAINS"),
        q(sub,ch,"Sample question 3 for ${'$'}ch","Option A","Option B","Option C","Option D",2,"Solution: C is correct.","EASY","JEE_MAINS"),
        q(sub,ch,"Sample question 4 for ${'$'}ch","Option A","Option B","Option C","Option D",0,"Solution: A is correct.","EASY","JEE_MAINS"),
        q(sub,ch,"Sample question 5 for ${'$'}ch","Option A","Option B","Option C","Option D",3,"Solution: D is correct.","EASY","JEE_MAINS"),
        q(sub,ch,"Medium question 1 for ${'$'}ch","Option A","Option B","Option C","Option D",1,"Solution explained.","MEDIUM","JEE_MAINS"),
        q(sub,ch,"Medium question 2 for ${'$'}ch","Option A","Option B","Option C","Option D",2,"Solution explained.","MEDIUM","JEE_MAINS"),
        q(sub,ch,"Medium question 3 for ${'$'}ch","Option A","Option B","Option C","Option D",0,"Solution explained.","MEDIUM","JEE_MAINS"),
        q(sub,ch,"Medium question 4 for ${'$'}ch","Option A","Option B","Option C","Option D",3,"Solution explained.","MEDIUM","JEE_MAINS"),
        q(sub,ch,"Medium question 5 for ${'$'}ch","Option A","Option B","Option C","Option D",1,"Solution explained.","MEDIUM","JEE_MAINS"),
        q(sub,ch,"Hard question 1 for ${'$'}ch","Option A","Option B","Option C","Option D",2,"Detailed solution.","HARD","JEE_MAINS"),
        q(sub,ch,"Hard question 2 for ${'$'}ch","Option A","Option B","Option C","Option D",0,"Detailed solution.","HARD","JEE_MAINS"),
        q(sub,ch,"Hard question 3 for ${'$'}ch","Option A","Option B","Option C","Option D",1,"Detailed solution.","HARD","JEE_MAINS"),
        q(sub,ch,"Hard question 4 for ${'$'}ch","Option A","Option B","Option C","Option D",3,"Detailed solution.","HARD","JEE_MAINS"),
        q(sub,ch,"Hard question 5 for ${'$'}ch","Option A","Option B","Option C","Option D",2,"Detailed solution.","HARD","JEE_MAINS"),
    )

    private val sampleBank: Map<String, List<QuestionEntity>> = mapOf(
        "Physics|Rotational Mechanics" to listOf(
            q("Physics","Rotational Mechanics","A uniform rod of mass m and length L is hinged at one end and released from horizontal. Angular velocity when vertical?","sqrt(3g/L)","sqrt(2g/L)","sqrt(g/L)","sqrt(6g/L)",0,"Energy conservation: mg(L/2) = (1/2)(mL^2/3)w^2, w = sqrt(3g/L)","EASY","JEE_MAINS"),
            q("Physics","Rotational Mechanics","Moment of inertia of a solid sphere about its diameter is?","(2/5)MR^2","(2/3)MR^2","(1/2)MR^2","MR^2",0,"Standard result: I = (2/5)MR^2 for solid sphere about diameter.","EASY","JEE_MAINS"),
            q("Physics","Rotational Mechanics","A disc rolls without slipping. Ratio of translational to rotational KE?","2:1","1:2","1:1","3:1",0,"KE_trans = (1/2)mv^2, KE_rot = (1/4)mv^2, ratio = 2:1.","EASY","JEE_MAINS"),
            q("Physics","Rotational Mechanics","Parallel axis theorem states I = ?","I_cm + Md^2","I_cm - Md^2","I_cm * d","I_cm / d",0,"Parallel axis theorem: I = I_cm + Md^2.","EASY","JEE_MAINS"),
            q("Physics","Rotational Mechanics","Angular momentum is conserved when?","No external torque","No external force","Constant velocity","Constant acceleration",0,"Angular momentum is conserved when net external torque is zero.","EASY","JEE_MAINS"),
            q("Physics","Rotational Mechanics","A flywheel has I=0.5 kg*m^2 and w=10 rad/s. Torque needed to stop it in 5s?","1 Nm","2 Nm","0.5 Nm","5 Nm",0,"alpha = 10/5 = 2, T = I*alpha = 0.5*2 = 1 Nm.","MEDIUM","JEE_MAINS"),
            q("Physics","Rotational Mechanics","Two discs of same mass but radii R and 2R. Ratio of their MOI about axis through center?","1:4","1:2","2:1","4:1",0,"I = (1/2)MR^2; ratio = R^2:(2R)^2 = 1:4.","MEDIUM","JEE_MAINS"),
            q("Physics","Rotational Mechanics","A body rolls down an incline. Which reaches bottom first?","Solid sphere","Hollow sphere","Solid cylinder","Ring",0,"Solid sphere has smallest k^2/R^2 = 2/5, so fastest.","MEDIUM","JEE_MAINS"),
            q("Physics","Rotational Mechanics","If torque is doubled and MOI is halved, angular acceleration becomes?","4 times","2 times","Same","Half",0,"alpha = T/I; if T -> 2T, I -> I/2, then alpha -> 4*alpha.","MEDIUM","JEE_MAINS"),
            q("Physics","Rotational Mechanics","Radius of gyration of a thin ring of radius R about its axis?","R","R/sqrt(2)","R*sqrt(2)","R/2",0,"For ring: I = MR^2, k = sqrt(I/M) = R.","MEDIUM","JEE_MAINS"),
            q("Physics","Rotational Mechanics","A disc and ring of same mass roll without slipping with same KE. Ratio of their velocities?","sqrt(4/3)","sqrt(3/4)","1","sqrt(2)",0,"For disc: KE=(3/4)mv^2, ring: KE=mv^2. Same KE => v_d/v_r = sqrt(4/3).","HARD","JEE_MAINS"),
            q("Physics","Rotational Mechanics","A rod of length L pivoted at end has angular acceleration 3g/(2L) at horizontal. What is the linear acceleration of its center?","(3g/4)*sqrt(1 + L^2w^4/g^2)","3g/4","g","3g/2",1,"At horizontal release: a_cm_tangential = alpha*(L/2) = 3g/4.","HARD","JEE_MAINS"),
            q("Physics","Rotational Mechanics","A particle moves in a circle of radius r with constant speed. Its angular momentum about center is?","mvr","mv^2r","mr^2/v","mv/r",0,"L = mvr for circular motion with r perpendicular to v.","HARD","JEE_MAINS"),
            q("Physics","Rotational Mechanics","Rolling constraint for pure rolling is v = ?","R*omega","R/omega","R*omega^2","omega/R",0,"For pure rolling without slipping: v = R*omega.","HARD","JEE_MAINS"),
            q("Physics","Rotational Mechanics","A uniform disc is rotating. If its radius somehow doubles (mass same), new omega?","omega/4","omega/2","2*omega","omega",0,"I1*w1 = I2*w2; (1/2)MR^2*w = (1/2)M(2R)^2*w2; w2 = w/4.","HARD","JEE_MAINS"),
            q("Physics","Rotational Mechanics","MOI of uniform rod about one end?","ML^2/3","ML^2/12","ML^2/2","ML^2",0,"Standard: I = ML^2/3 about one end.","EASY","JEE_ADVANCED"),
            q("Physics","Rotational Mechanics","Torque on a body is r x F. If r and F are parallel, torque is?","Zero","rF","F/r","Infinite",0,"Cross product of parallel vectors is zero.","EASY","JEE_ADVANCED"),
            q("Physics","Rotational Mechanics","A solid cylinder rolls up an incline. What fraction of initial KE is rotational?","1/3","1/2","2/3","1/4",0,"KE_rot/KE_total = (1/4)mv^2 / (3/4)mv^2 = 1/3.","EASY","JEE_ADVANCED"),
            q("Physics","Rotational Mechanics","Perpendicular axis theorem applies to?","Planar bodies","3D bodies","Point masses","All bodies",0,"Perpendicular axis theorem is only for planar (2D) bodies.","EASY","JEE_ADVANCED"),
            q("Physics","Rotational Mechanics","If no external torque acts, which is conserved?","Angular momentum","Angular velocity","Torque","MOI",0,"No external torque => angular momentum is conserved.","EASY","JEE_ADVANCED"),
            q("Physics","Rotational Mechanics","A cylinder rolls without slipping on a horizontal surface. The velocity of the contact point is?","Zero","v","2v","v/2",0,"For pure rolling, the contact point has zero velocity.","MEDIUM","JEE_ADVANCED"),
            q("Physics","Rotational Mechanics","A uniform rod falls from vertical position pivoted at lower end. Speed of upper end when horizontal?","sqrt(3gL)","sqrt(2gL)","sqrt(gL)","sqrt(6gL)",0,"Using energy: mg(L/2)=(1/2)(ML^2/3)w^2 => w=sqrt(3g/L), v=wL=sqrt(3gL).","MEDIUM","JEE_ADVANCED"),
            q("Physics","Rotational Mechanics","Two identical discs rotate in opposite directions with same omega. If combined, final omega?","Zero","omega","omega/2","2*omega",0,"L_total=Iw-Iw=0, so final omega=0.","MEDIUM","JEE_ADVANCED"),
            q("Physics","Rotational Mechanics","Power delivered by torque T at angular velocity w?","T*w","T/w","T*w^2","T^2*w",0,"Power P = Torque * angular velocity = Tw.","MEDIUM","JEE_ADVANCED"),
            q("Physics","Rotational Mechanics","Work done by torque T through angle theta?","T*theta","T/theta","T*theta^2","theta/T",0,"Work W = T * theta for constant torque.","MEDIUM","JEE_ADVANCED"),
            q("Physics","Rotational Mechanics","A disc has angular momentum L. If radius halved keeping mass same, new L with same omega?","L/4","L/2","L","2L",0,"I_new=(1/2)M(R/2)^2 = I/4. L_new = I_new*w = L/4.","HARD","JEE_ADVANCED"),
            q("Physics","Rotational Mechanics","For a solid sphere rolling without slipping, fraction of total KE that is translational?","5/7","2/7","1/2","2/5",0,"KE_trans/KE_total = (1/2)mv^2/((1/2)mv^2+(1/5)mv^2) = 5/7.","HARD","JEE_ADVANCED"),
            q("Physics","Rotational Mechanics","A disc rotates with angular velocity w. A bug walks from edge to center. What happens to w?","Increases","Decreases","Stays same","Becomes zero",0,"MOI decreases as bug moves inward, so w increases (L conserved).","HARD","JEE_ADVANCED"),
            q("Physics","Rotational Mechanics","Moment of inertia of a thin spherical shell about diameter?","(2/3)MR^2","(2/5)MR^2","MR^2","(1/2)MR^2",0,"Standard: I = (2/3)MR^2 for thin spherical shell about diameter.","HARD","JEE_ADVANCED"),
            q("Physics","Rotational Mechanics","For a body in combined translational and rotational motion, KE = ?","(1/2)mv_cm^2 + (1/2)I_cm*w^2","(1/2)mv^2","(1/2)Iw^2","mv^2+Iw^2",0,"KE = (1/2)mv_cm^2 + (1/2)I_cm*w^2 is the general expression.","HARD","JEE_ADVANCED"),
        ),
        "Physics|Kinematics" to listOf(
            q("Physics","Kinematics","A ball is thrown vertically up with velocity u. Max height?","u^2/(2g)","u/g","u^2/g","2u^2/g",0,"v^2=u^2-2gh, at max h: h=u^2/(2g).","EASY","JEE_MAINS"),
            q("Physics","Kinematics","Velocity-time graph is a straight line with positive slope. Motion is?","Uniformly accelerated","Uniform velocity","Decelerated","At rest",0,"Positive slope in v-t means constant positive acceleration.","EASY","JEE_MAINS"),
            q("Physics","Kinematics","Range of projectile at 45 degrees?","u^2/g","u^2/(2g)","2u^2/g","u/(2g)",0,"R = u^2*sin(2*45)/g = u^2/g.","EASY","JEE_MAINS"),
            q("Physics","Kinematics","Displacement in nth second: s_n = ?","u + a(2n-1)/2","u + an","ut + at^2/2","u + at",0,"Displacement in nth second = u + a(2n-1)/2.","EASY","JEE_MAINS"),
            q("Physics","Kinematics","Two projectiles at complementary angles have same?","Range","Max height","Time of flight","All of these",0,"Projectiles at theta and (90-theta) have equal range.","EASY","JEE_MAINS"),
            q("Physics","Kinematics","A car accelerates from rest at 2 m/s^2. Distance in 5 seconds?","25 m","10 m","50 m","20 m",0,"s = (1/2)*2*25 = 25 m.","MEDIUM","JEE_MAINS"),
            q("Physics","Kinematics","Time of flight of projectile launched at angle theta with speed u?","2u*sin(theta)/g","u*sin(theta)/g","2u/g","u^2*sin(theta)/g",0,"T = 2u*sin(theta)/g.","MEDIUM","JEE_MAINS"),
            q("Physics","Kinematics","Relative velocity of A wrt B if both move in same direction at 5 and 3 m/s?","2 m/s","8 m/s","15 m/s","1 m/s",0,"v_AB = 5 - 3 = 2 m/s in same direction.","MEDIUM","JEE_MAINS"),
            q("Physics","Kinematics","Area under v-t graph gives?","Displacement","Velocity","Acceleration","Force",0,"Area under velocity-time graph equals displacement.","MEDIUM","JEE_MAINS"),
            q("Physics","Kinematics","A body falls freely for 2s. Distance fallen (g=10)?","20 m","10 m","40 m","5 m",0,"s = (1/2)*10*4 = 20 m.","MEDIUM","JEE_MAINS"),
            q("Physics","Kinematics","Projectile is launched at 60 degrees. Angle of velocity with horizontal at highest point?","0 degrees","30 degrees","60 degrees","90 degrees",0,"At highest point, vertical component is zero, so angle = 0.","HARD","JEE_MAINS"),
            q("Physics","Kinematics","Two balls dropped from heights h and 4h simultaneously. Ratio of time to reach ground?","1:2","2:1","1:4","1:sqrt(2)",0,"t = sqrt(2h/g), ratio = sqrt(h):sqrt(4h) = 1:2.","HARD","JEE_MAINS"),
            q("Physics","Kinematics","A boat crosses a river of width d. If river velocity is v_r and boat velocity is v_b, minimum time?","d/v_b","d/v_r","d/(v_b+v_r)","d*v_r/v_b",0,"Minimum time when boat points straight across: t = d/v_b.","HARD","JEE_MAINS"),
            q("Physics","Kinematics","Average velocity for first half distance at v1 and second half at v2?","2*v1*v2/(v1+v2)","(v1+v2)/2","sqrt(v1*v2)","v1+v2",0,"For equal distances: v_avg = 2*v1*v2/(v1+v2).","HARD","JEE_MAINS"),
            q("Physics","Kinematics","If acceleration is proportional to displacement, motion is?","SHM","Uniform","Uniformly accelerated","Random",0,"a proportional to -x gives SHM; a proportional to x gives exponential.","HARD","JEE_MAINS"),
        ),
        "Chemistry|Atomic Structure" to listOf(
            q("Chemistry","Atomic Structure","Heisenberg uncertainty principle states?","dx*dp >= h/(4*pi)","E = mc^2","E = hv","F = ma",0,"Uncertainty principle: dx*dp >= h-bar/2 = h/(4*pi).","EASY","JEE_MAINS"),
            q("Chemistry","Atomic Structure","Number of orbitals in 3rd shell?","9","3","6","12",0,"n^2 = 3^2 = 9 orbitals.","EASY","JEE_MAINS"),
            q("Chemistry","Atomic Structure","Shape of s orbital?","Spherical","Dumbbell","Cloverleaf","Linear",0,"s orbitals are spherically symmetrical.","EASY","JEE_MAINS"),
            q("Chemistry","Atomic Structure","de Broglie wavelength formula?","h/(mv)","hv","mc^2","h*v/c",0,"lambda = h/(mv) = h/p.","EASY","JEE_MAINS"),
            q("Chemistry","Atomic Structure","Max electrons in a subshell = ?","4l+2","2l+1","2n^2","n+l",0,"Max electrons in subshell = 2(2l+1) = 4l+2.","EASY","JEE_MAINS"),
            q("Chemistry","Atomic Structure","Aufbau principle determines?","Order of orbital filling","Shape of orbital","Spin of electron","Nuclear stability",0,"Aufbau principle gives the order in which orbitals are filled.","MEDIUM","JEE_MAINS"),
            q("Chemistry","Atomic Structure","Which quantum number determines the shape of orbital?","l (azimuthal)","n (principal)","m (magnetic)","s (spin)",0,"Azimuthal quantum number l determines orbital shape.","MEDIUM","JEE_MAINS"),
            q("Chemistry","Atomic Structure","Bohr radius of hydrogen atom (first orbit)?","0.529 Angstrom","1.06 Angstrom","2.12 Angstrom","0.265 Angstrom",0,"a0 = 0.529 Angstrom is the first Bohr radius.","MEDIUM","JEE_MAINS"),
            q("Chemistry","Atomic Structure","Hund's rule states?","Maximum multiplicity","Electrons pair first","Orbitals fill in order","No two electrons same state",0,"Hund's rule: electrons occupy orbitals singly first (max multiplicity).","MEDIUM","JEE_MAINS"),
            q("Chemistry","Atomic Structure","Energy of electron in nth orbit of H-atom?","-13.6/n^2 eV","13.6*n^2 eV","-13.6*n eV","13.6/n eV",0,"En = -13.6/n^2 eV for hydrogen.","MEDIUM","JEE_MAINS"),
            q("Chemistry","Atomic Structure","Photoelectric effect proves light is?","Particle (photon)","Wave","Both","Neither",0,"Photoelectric effect demonstrates particle nature of light.","HARD","JEE_MAINS"),
            q("Chemistry","Atomic Structure","For multi-electron atoms, energy depends on?","n + l","n only","l only","m",0,"In multi-electron atoms, energy depends on both n and l (n+l rule).","HARD","JEE_MAINS"),
            q("Chemistry","Atomic Structure","Number of radial nodes for 3p orbital?","1","2","0","3",0,"Radial nodes = n - l - 1 = 3 - 1 - 1 = 1.","HARD","JEE_MAINS"),
            q("Chemistry","Atomic Structure","Effective nuclear charge increases across a period because?","Shielding is imperfect","Electrons added to same shell","Nuclear charge increases","All of these",3,"All factors contribute to increasing Zeff across a period.","HARD","JEE_MAINS"),
            q("Chemistry","Atomic Structure","Probability of finding electron at nucleus for s orbital?","Maximum","Zero","Half","Undefined",0,"s orbital has maximum probability density at nucleus (but shell probability is zero).","HARD","JEE_MAINS"),
        ),
        "Maths|Limits and Continuity" to listOf(
            q("Maths","Limits and Continuity","lim(x->0) sin(x)/x = ?","1","0","infinity","Does not exist",0,"Standard limit: sin(x)/x -> 1 as x -> 0.","EASY","JEE_MAINS"),
            q("Maths","Limits and Continuity","lim(x->0) (1-cos(x))/x^2 = ?","1/2","1","0","2",0,"Using L'Hopital or series: (1-cosx)/x^2 -> 1/2.","EASY","JEE_MAINS"),
            q("Maths","Limits and Continuity","A function is continuous at x=a if?","lim f(x) = f(a)","f(a) exists","lim exists","f is differentiable",0,"Continuity requires lim(x->a) f(x) = f(a).","EASY","JEE_MAINS"),
            q("Maths","Limits and Continuity","lim(x->inf) (1 + 1/x)^x = ?","e","1","infinity","0",0,"This is the definition of e.","EASY","JEE_MAINS"),
            q("Maths","Limits and Continuity","lim(x->0) tan(x)/x = ?","1","0","infinity","pi",0,"tan(x)/x -> sin(x)/(x*cos(x)) -> 1*1 = 1.","EASY","JEE_MAINS"),
            q("Maths","Limits and Continuity","lim(x->0) (e^x - 1)/x = ?","1","e","0","infinity",0,"Standard limit: (e^x - 1)/x -> 1.","MEDIUM","JEE_MAINS"),
            q("Maths","Limits and Continuity","lim(x->0) ln(1+x)/x = ?","1","0","e","infinity",0,"Standard limit: ln(1+x)/x -> 1.","MEDIUM","JEE_MAINS"),
            q("Maths","Limits and Continuity","Sandwich theorem is used when?","f(x) is bounded between two functions with same limit","f is discontinuous","f is not defined","Always",0,"Squeeze/sandwich theorem works when g(x) <= f(x) <= h(x) and lim g = lim h.","MEDIUM","JEE_MAINS"),
            q("Maths","Limits and Continuity","lim(x->0) x*sin(1/x) = ?","0","1","Does not exist","-1",0,"Since -|x| <= x*sin(1/x) <= |x|, by squeeze theorem limit is 0.","MEDIUM","JEE_MAINS"),
            q("Maths","Limits and Continuity","IVT guarantees for continuous f on [a,b] that?","f takes all values between f(a) and f(b)","f has a maximum","f is differentiable","f is bounded",0,"Intermediate Value Theorem: continuous function takes all intermediate values.","MEDIUM","JEE_MAINS"),
            q("Maths","Limits and Continuity","lim(x->0) (a^x - 1)/x = ?","ln(a)","log(a)","a","1/a",0,"Standard: (a^x - 1)/x -> ln(a).","HARD","JEE_MAINS"),
            q("Maths","Limits and Continuity","lim(x->0) (1+x)^(1/x) = ?","e","1","infinity","0",0,"(1+x)^(1/x) -> e as x -> 0 by definition.","HARD","JEE_MAINS"),
            q("Maths","Limits and Continuity","If f(x) = x^2*sin(1/x) for x!=0, f(0)=0, then f is?","Continuous and differentiable at 0","Only continuous","Only differentiable","Neither",0,"f'(0) = lim h*sin(1/h) = 0, so differentiable; also continuous.","HARD","JEE_MAINS"),
            q("Maths","Limits and Continuity","Number of points where f(x)=|x-1|+|x-2| is not differentiable?","2","1","0","3",0,"Not differentiable at x=1 and x=2 (corners of absolute value).","HARD","JEE_MAINS"),
            q("Maths","Limits and Continuity","lim(n->inf) (1 + 2/n)^n = ?","e^2","2e","e","infinity",0,"(1 + 2/n)^n = ((1+2/n)^(n/2))^2 -> e^2.","HARD","JEE_MAINS"),
        ),
    )
}
