<h1>Vitalizr</h1>
<p> As my mother's health has deteriorated, it became more and more important to capture information about her vitals.
While most people are content to wait to learn their blood pressure, heart rate, blood oxygen levels, and other vitals
when they visit their doctor's office, in order to care for mom effectively, I want to know these measurements more often.
Treating her diabetes means managing her blood sugar levels; such is a daily, sometimes hourly, task.</p>

<p>I want a system that makes it easy to record her vitals and analyze this data. Now, it's true, I can easily use pen & paper.
Pen and paper is essentially what I've been using, a whiteboard with dry erase markers that I capture numbers as I collect them.
This has all the usual obvious problems: what happens if an entry gets erased accidentally? What if my handwriting is bad?
What if I run out of space? How do I share it simply with her doctors? How can I see the underlying trends in the data?</p>

<p>I could easily use a spreadsheet. That's suggested to be the "lowest effort" electronic solution.
It solves digital durability, clarity, is conceptually more difficult to run out of space and easier to share.</p>

<p>But it still involves tedious, manual work. I have to take readings, copy them down into a spreadsheet. If I want to make
a graph, I have to learn Excel's charting tools. I could do fancy manipulations with Pivot Tables, etc...</p>

<p>But what I really want is a way for all of this to be done for me. I don't want to have to take her blood pressure
using a blood pressure cuff, read and copy down the numbers, and then send them to a physician later. In my ideal world,
the cuff is integrated with my data store. When I do a reading, it persists its measurement to a log.
That log is automatically shared with family and trusted contacts like doctors. The log has machine learning applied to
find patterns and surface correlations/offer tentative causality hypotheses. The auto-collection of data prevents me from
making silly mistakes and can integrate with other systems that take actions based on the readings. I want to be able
to visualize her readings for weight and blood pressure over a week, a month, a year.
I want to be able to correlate specific events, like hospitalization or illness, with changes in median levels. Speaking
of medians, I want to be able to simply calculate summary statistics.</p>

<p>From information, we can achieve freedom. So let's build a system that makes it simple to gather this information and easy
to use it to improve someone's health.</p>

<h2>Usage</h2>
<h3>Server side</h3>
<ul>
<li><code>java -cp build/libs/vitalizr-0.0.1-SNAPSHOT.jar software.visionary.iluvatar.Iluvatar 13338 AddWeight</code></li>
<li><code>java -cp build/libs/vitalizr-0.0.1-SNAPSHOT.jar software.visionary.iluvatar.Iluvatar 13339 ListWeights</code></li>
<li><code>java -cp build/libs/vitalizr-0.0.1-SNAPSHOT.jar software.visionary.iluvatar.Iluvatar 13340 AddBodyMassIndex</code></li>
<li><code>java -cp build/libs/vitalizr-0.0.1-SNAPSHOT.jar software.visionary.iluvatar.Iluvatar 13341 ListBodyMassIndices</code></li>
<li><code>java -cp build/libs/vitalizr-0.0.1-SNAPSHOT.jar software.visionary.iluvatar.Iluvatar 13342 AddBodyFatPercentage</code></li>
<li><code>java -cp build/libs/vitalizr-0.0.1-SNAPSHOT.jar software.visionary.iluvatar.Iluvatar 13343 ListBodyFatPercentages</code></li>
<li><code>java -cp build/libs/vitalizr-0.0.1-SNAPSHOT.jar software.visionary.iluvatar.Iluvatar 13344 AddBodyWaterPercentage</code></li>
<li><code>java -cp build/libs/vitalizr-0.0.1-SNAPSHOT.jar software.visionary.iluvatar.Iluvatar 13345 ListBodyWaterPercentages</code></li>
<li><code>java -cp build/libs/vitalizr-0.0.1-SNAPSHOT.jar software.visionary.iluvatar.Iluvatar 13346 AddBloodSugar</code></li>
<li><code>java -cp build/libs/vitalizr-0.0.1-SNAPSHOT.jar software.visionary.iluvatar.Iluvatar 13347 ListBloodSugars</code></li>
<li><code>java -cp build/libs/vitalizr-0.0.1-SNAPSHOT.jar software.visionary.iluvatar.Iluvatar 13348 AddBloodPressure</code></li>
<li><code>java -cp build/libs/vitalizr-0.0.1-SNAPSHOT.jar software.visionary.iluvatar.Iluvatar 13349 ListBloodPressures</code></li>
<li><code>java -cp build/libs/vitalizr-0.0.1-SNAPSHOT.jar software.visionary.iluvatar.Iluvatar 13350 AddBodyTemperature</code></li>
<li><code>java -cp build/libs/vitalizr-0.0.1-SNAPSHOT.jar software.visionary.iluvatar.Iluvatar 13351 ListBodyTemperatures</code></li>
<li><code>java -cp build/libs/vitalizr-0.0.1-SNAPSHOT.jar software.visionary.iluvatar.Iluvatar 13352 AddBloodOxygen</code></li>
<li><code>java -cp build/libs/vitalizr-0.0.1-SNAPSHOT.jar software.visionary.iluvatar.Iluvatar 13353 ListBloodOxygens</code></li>
<li><code>java -cp build/libs/vitalizr-0.0.1-SNAPSHOT.jar software.visionary.iluvatar.Iluvatar 13354 AddPulse</code></li>
<li><code>java -cp build/libs/vitalizr-0.0.1-SNAPSHOT.jar software.visionary.iluvatar.Iluvatar 13355 ListPulses</code></li>
</ul>

<p>This project uses <strong><code>software.visionary.iluvatar.Iluvatar</code></strong> as a plugin-based lightweight Socket listener. Each Vital's actions 
are implemented as a micro-service, which can start as an independent process and accept connections. The full list of
services and their associated ports are documented in the <code>software.visionary.vitalizr.Client</code>, which shows the basic
communication model and protocol.</p>
<h3>Client Side</h3>
<ul>
<li><code>java -cp build/libs/vitalizr-0.0.1-SNAPSHOT.jar software.visionary.vitalizr.Client "List" "Weight" "7ab35698-21f9-463e-8e74-bd3d56109336"</code></li>
<li><code>java -cp build/libs/vitalizr-0.0.1-SNAPSHOT.jar software.visionary.vitalizr.Client "Add" "Fat" "7ab35698-21f9-463e-8e74-bd3d56109336" "28.5"</code></li>
<li><code>java -cp build/libs/vitalizr-0.0.1-SNAPSHOT.jar software.visionary.vitalizr.Client "List" "Fat" "7ab35698-21f9-463e-8e74-bd3d56109336"</code></li>
<li><code>java -cp build/libs/vitalizr-0.0.1-SNAPSHOT.jar software.visionary.vitalizr.Client "Add" "BMI" "7ab35698-21f9-463e-8e74-bd3d56109336" "31.8"</code></li>
<li><code>java -cp build/libs/vitalizr-0.0.1-SNAPSHOT.jar software.visionary.vitalizr.Client "List" "Water" "7ab35698-21f9-463e-8e74-bd3d56109336"</code></li>
</ul>
<p>The client also supports adding a new Person who hasn't previously been stored.</p>
<ol>
<li>Generate a UUID externally first for them.(TODO, encapsulate this) For instance, in Ubuntu you can use <code>uuidgen</code></li>
<li>Send a request with the id and their info, like: 
<code>java -cp build/libs/vitalizr-0.0.1-SNAPSHOT.jar software.visionary.vitalizr.Client "Add" "Sugar" "mom:1959-1-9:mom@mommy.net" "91"</code></li>
</ol>
<h3>Debug</h3>
<p>To debug/poke around, git clone the repo, run one of the microservices, and attach a debugger like so:</p>
<code>java -cp build/libs/vitalizr-0.0.1-SNAPSHOT.jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000 software.visionary.iluvatar.Iluvatar 13338 AddWeight</code>
<h3>Questions</h3>
<p>Contact <strong><em>nick</em></strong> at <code>visionary.software</code>.
<strong>Vitalizr</strong> is a loving creation of my own mind that to solve some of my family's problems, that may help yours. It is licensed under <strong>LGPLv3</strong> because
I'm a frim believer in Free Software and a Free And Open Internet/Society.</p>
<p>Vitalizr is provided with an "AS-IS" Warranty, you break it, you bought it. I do not condone any capitalists trying
to incorporate it into their work to rip off the masses. I will, however, accept donations that will enable me to do other useful things, if you'd be inclined to Flattr me: <a href="https://flattr.com/@visionary">flattr.com/@visionary</a></p>
<p>If you would like to commission me to realize one of your visions, I am open to independent project contracts.</p>