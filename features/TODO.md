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

<h2>User Stories</h2>
<p>As a patient, because I want to proactively monitor my health, I want to be able to store my O2 at a particular
point in time.</p>
<p>As a patient, because I want to review my health, I want to be retrieve my O2 for a time range.</p>
<p>As a software engineer, because I don't want to copy-paste code and die in maintenance obscurity, I want weight,
blood pressure, pulse, and O2 to unify behind a common concept, Vitals</p>
<p>As a patient, because I want to be able to compare my Vitals over time, I want to be able to see a line graph for a
time range.</p>
<p>As family of a patient, because I love my patient health, I want to be able to review my patient's Vitals
for a time range</p>
<p>As a doctor of the patient, in order to make advanced clinical decisions, I want to be able to review my patient's Vitals
for a time range</p>
<p>As a trusted contact, in order review the patient's health, I want to be able to review a patient's Vitals
for a time range</p>
<p>As a patient, because I don't want to forget, I would like to schedule reminders for when I should collect Vitals</p>
<p>As a patient, because even reminders are easy to forget to check, I want reminders to automatically push to check my vitals</p>
<p>As a human being, because all of this work is tedious and mechanical, I would like a configurable automatic system to periodically
record my vitals</p>
<p>As a patient, because I value my privacy, I want my information to be encrypted and viewable only by me and trusted contacts</p>
<p>As someone who cares about fitness, I because I want to optimize my regimen, I want to be able to review my vitals</p>
<p>As a software engineer, because I want to use Vitals in my third-party app integrations, I want an API I can use to
retrieve Vitals</p>
<p>As a human being, because I value my privacy, I want to be able to authenticate as a Principal by presenting my Credentials</p>
<p>As a human being, because I value my privacy, I want access to my information to be allowed only to authorized Principals</p>
<p>As a software engineer, for legal idemnification, I want actions by Principals in the system to be audited.</p>


