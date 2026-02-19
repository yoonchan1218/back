const playwrightPath = 'C:\\Users\\pigch\\AppData\\Local\\npm-cache\\_npx\\e41f203b7505f1fb\\node_modules\\playwright';
const { chromium } = require(playwrightPath);
const path = require('path');
const fs = require('fs');

// Create screenshots directory
const screenshotsDir = path.join(__dirname, 'playwright-screenshots');
if (!fs.existsSync(screenshotsDir)) {
    fs.mkdirSync(screenshotsDir, { recursive: true });
}

async function runTest() {
    const browser = await chromium.launch({ headless: true });
    const context = await browser.newContext({
        viewport: { width: 1280, height: 900 }
    });
    const page = await context.newPage();

    const report = [];

    function log(msg) {
        console.log(msg);
        report.push(msg);
    }

    try {
        // Step 1: Navigate to login page
        log('\n=== STEP 1: Navigate to login page ===');
        await page.goto('http://localhost:10000/main/log-in', { waitUntil: 'networkidle', timeout: 15000 });
        const loginTitle = await page.title();
        log(`Page title: ${loginTitle}`);
        log(`Current URL: ${page.url()}`);

        // Take screenshot
        await page.screenshot({ path: path.join(screenshotsDir, '01-login-page.png'), fullPage: true });
        log('Screenshot saved: 01-login-page.png');

        // Step 2: Inspect login form
        log('\n=== STEP 2: Inspect login form fields ===');
        const formFields = await page.evaluate(() => {
            const form = document.getElementById('login-form');
            if (!form) return { found: false };

            const inputs = Array.from(form.querySelectorAll('input[type="text"], input[type="password"], input[type="email"]'));
            return {
                found: true,
                formAction: form.action,
                formMethod: form.method,
                fields: inputs.map(i => ({
                    id: i.id,
                    name: i.name,
                    type: i.type,
                    placeholder: i.placeholder,
                    value: i.value
                }))
            };
        });
        log(`Login form found: ${formFields.found}`);
        if (formFields.found) {
            log(`Form action: ${formFields.formAction}`);
            log(`Form method: ${formFields.formMethod}`);
            log('Form fields:');
            formFields.fields.forEach(f => {
                log(`  - id="${f.id}" name="${f.name}" type="${f.type}" placeholder="${f.placeholder}" value="${f.value}"`);
            });
        }

        // Check tabs
        const tabs = await page.evaluate(() => {
            return Array.from(document.querySelectorAll('#devMemTab li a')).map(a => a.textContent.trim());
        });
        log(`Login tabs: ${tabs.join(', ')}`);

        // Step 3: Try to log in
        log('\n=== STEP 3: Attempting login with test/1234 ===');
        await page.fill('#M_ID', 'test');
        await page.fill('#M_PWD', '1234');
        log('Filled in credentials: memberId=test, memberPassword=1234');

        // Take screenshot before submit
        await page.screenshot({ path: path.join(screenshotsDir, '02-before-login.png'), fullPage: true });
        log('Screenshot saved: 02-before-login.png');

        await page.click('button[type="submit"]');
        await page.waitForNavigation({ timeout: 10000, waitUntil: 'networkidle' }).catch(() => {
            log('Navigation timed out or did not occur after login submit');
        });

        const afterLoginUrl = page.url();
        log(`URL after login attempt: ${afterLoginUrl}`);

        // Take screenshot after login
        await page.screenshot({ path: path.join(screenshotsDir, '03-after-login.png'), fullPage: true });
        log('Screenshot saved: 03-after-login.png');

        const loginSuccess = !afterLoginUrl.includes('/log-in');
        log(`Login successful: ${loginSuccess}`);

        if (!loginSuccess) {
            log('\n=== Login failed, checking for error messages ===');
            const errorText = await page.evaluate(() => {
                const err = document.querySelector('.text-error');
                return err ? err.textContent.trim() : 'No error element found';
            });
            log(`Error message: ${errorText}`);

            // Try with admin credentials
            log('\n=== Trying admin/admin credentials ===');
            await page.fill('#M_ID', 'admin');
            await page.fill('#M_PWD', 'admin');
            await page.click('button[type="submit"]');
            await page.waitForNavigation({ timeout: 8000, waitUntil: 'networkidle' }).catch(() => {});
            log(`URL after admin login: ${page.url()}`);
            await page.screenshot({ path: path.join(screenshotsDir, '03b-after-admin-login.png'), fullPage: true });

            if (!page.url().includes('/log-in')) {
                log('Login succeeded with admin/admin');
            } else {
                // Try user1/1234
                log('\n=== Trying user1/1234 credentials ===');
                await page.goto('http://localhost:10000/main/log-in', { waitUntil: 'networkidle' });
                await page.fill('#M_ID', 'user1');
                await page.fill('#M_PWD', '1234');
                await page.click('button[type="submit"]');
                await page.waitForNavigation({ timeout: 8000, waitUntil: 'networkidle' }).catch(() => {});
                log(`URL after user1/1234 login: ${page.url()}`);
                await page.screenshot({ path: path.join(screenshotsDir, '03c-after-user1-login.png'), fullPage: true });
            }
        }

        const isLoggedIn = !page.url().includes('/log-in');
        log(`\nFinal login status: ${isLoggedIn ? 'LOGGED IN' : 'NOT LOGGED IN'}`);
        log(`Current URL: ${page.url()}`);

        // Step 4: Navigate to change-my-information
        log('\n=== STEP 4: Navigate to change-my-information page ===');
        await page.goto('http://localhost:10000/mypage/change-my-information', {
            waitUntil: 'networkidle',
            timeout: 15000
        });
        const cmiUrl = page.url();
        log(`URL after navigation: ${cmiUrl}`);
        log(`Redirected to login: ${cmiUrl.includes('/log-in')}`);

        // Take screenshot
        await page.screenshot({ path: path.join(screenshotsDir, '04-change-my-information.png'), fullPage: true });
        log('Screenshot saved: 04-change-my-information.png');

        // Step 5: Inspect address fields
        log('\n=== STEP 5: Inspect address and form fields ===');
        const pageTitle = await page.title();
        log(`Page title: ${pageTitle}`);

        if (!cmiUrl.includes('/log-in')) {
            // Check address fields
            const addressFields = await page.evaluate(() => {
                const getField = (id) => {
                    const el = document.getElementById(id);
                    if (!el) return { found: false, id };
                    return {
                        found: true,
                        id: el.id,
                        name: el.name,
                        type: el.type || el.tagName,
                        value: el.value,
                        placeholder: el.placeholder || ''
                    };
                };

                return {
                    postcheck: getField('postcheck'),
                    M_AddrText: getField('M_Addr_Text'),
                    M_AddrText1: getField('M_Addr_Text1'),
                    M_AddrType: getField('M_AddrType')
                };
            });

            log('Address fields:');
            Object.entries(addressFields).forEach(([key, val]) => {
                if (val.found) {
                    log(`  - ${key}: id="${val.id}" name="${val.name}" value="${val.value}" placeholder="${val.placeholder}"`);
                } else {
                    log(`  - ${key}: NOT FOUND`);
                }
            });

            // Check gender/birth/phone/education fields
            log('\nChecking gender, birth, education, phone fields:');
            const otherFields = await page.evaluate(() => {
                const getInputField = (id) => {
                    const el = document.getElementById(id);
                    if (!el) return null;
                    return { id: el.id, value: el.value, tagName: el.tagName };
                };

                const getSelectField = (id) => {
                    const el = document.getElementById(id);
                    if (!el) return null;
                    const selectedOpt = el.options[el.selectedIndex];
                    return {
                        id: el.id,
                        value: el.value,
                        selectedText: selectedOpt ? selectedOpt.text : '',
                        tagName: el.tagName
                    };
                };

                const getRadioField = (name) => {
                    const radios = document.querySelectorAll(`input[name="${name}"]`);
                    const checked = Array.from(radios).find(r => r.checked);
                    return {
                        name,
                        checkedValue: checked ? checked.value : 'none checked',
                        count: radios.length
                    };
                };

                // Name field
                const nameField = getInputField('M_Name');

                // Birth year/month/day
                const birthYear = getSelectField('M_Year');
                const birthMonth = getSelectField('M_Month');
                const birthDay = getSelectField('M_Day');

                // Gender / born radio
                const genderRadio = getRadioField('M_Born');

                // Education
                const education = getSelectField('M_Education');

                // Phone (mobile and home)
                const phone1 = getInputField('M_Hand_Phone');
                const homePhone1 = document.getElementById('M_Home_Phone1') ?
                    getInputField('M_Home_Phone1') : null;

                // Email
                const email = getInputField('M_Email');

                // MemberId (display)
                const memberIdEl = document.querySelector('strong[th\\:text]') ||
                                   document.querySelector('.social_row strong');

                return {
                    nameField,
                    birthYear,
                    birthMonth,
                    birthDay,
                    genderRadio,
                    education,
                    phone1,
                    homePhone1,
                    email,
                    memberIdDisplay: memberIdEl ? memberIdEl.textContent.trim() : 'not found'
                };
            });

            log(`  memberId displayed: "${otherFields.memberIdDisplay}"`);
            if (otherFields.nameField) log(`  Name (M_Name): value="${otherFields.nameField.value}"`);
            if (otherFields.birthYear) log(`  Birth Year (M_Year): value="${otherFields.birthYear.value}" selected="${otherFields.birthYear.selectedText}"`);
            if (otherFields.birthMonth) log(`  Birth Month (M_Month): value="${otherFields.birthMonth.value}" selected="${otherFields.birthMonth.selectedText}"`);
            if (otherFields.birthDay) log(`  Birth Day (M_Day): value="${otherFields.birthDay.value}" selected="${otherFields.birthDay.selectedText}"`);
            if (otherFields.genderRadio) log(`  Gender radio (M_Born): checked="${otherFields.genderRadio.checkedValue}" count=${otherFields.genderRadio.count}`);
            if (otherFields.education) log(`  Education (M_Education): value="${otherFields.education.value}" selected="${otherFields.education.selectedText}"`);
            if (otherFields.phone1) log(`  Phone hidden (M_Hand_Phone): value="${otherFields.phone1.value}"`);
            if (otherFields.email) log(`  Email (M_Email): value="${otherFields.email.value}"`);

            // Check th:value rendering by looking at actual input values
            log('\nChecking Thymeleaf th:value binding rendering:');
            const thymeleafBindings = await page.evaluate(() => {
                const results = {};

                // Check all inputs with th:value (they should have rendered values if populated)
                const inputs = document.querySelectorAll('input[id]');
                inputs.forEach(inp => {
                    if (inp.id && inp.type !== 'hidden') {
                        results[inp.id] = {
                            type: inp.type,
                            value: inp.value,
                            hasValue: inp.value.length > 0
                        };
                    }
                });
                return results;
            });

            log('Visible input fields and values:');
            Object.entries(thymeleafBindings).forEach(([id, info]) => {
                log(`  - #${id} (${info.type}): value="${info.value}" hasValue=${info.hasValue}`);
            });

            // Check for any errors on the page
            const errors = await page.evaluate(() => {
                const errEls = document.querySelectorAll('.error, .alert, .warning, [class*="error"]');
                return Array.from(errEls).map(el => el.textContent.trim()).filter(t => t.length > 0);
            });
            if (errors.length > 0) {
                log('\nErrors/alerts found on page:');
                errors.forEach(e => log(`  - ${e}`));
            }

        } else {
            log('Redirected to login page - not logged in. Cannot inspect change-my-information page.');

            // Show what fields are visible on the redirected page
            const redirectedPageContent = await page.evaluate(() => {
                return {
                    url: window.location.href,
                    title: document.title,
                    hasLoginForm: !!document.getElementById('login-form')
                };
            });
            log(`Redirect info: ${JSON.stringify(redirectedPageContent)}`);
        }

        // Final screenshot
        await page.screenshot({ path: path.join(screenshotsDir, '05-final-state.png'), fullPage: true });
        log('\nScreenshot saved: 05-final-state.png');

    } catch (err) {
        log(`\nERROR: ${err.message}`);
        await page.screenshot({ path: path.join(screenshotsDir, 'error-state.png'), fullPage: true }).catch(() => {});
    } finally {
        await browser.close();
    }

    log('\n=== TEST COMPLETE ===');
    return report.join('\n');
}

runTest().then(report => {
    const reportPath = path.join(__dirname, 'playwright-report.txt');
    require('fs').writeFileSync(reportPath, report);
    console.log('\nReport written to:', reportPath);
}).catch(err => {
    console.error('Fatal error:', err);
});
